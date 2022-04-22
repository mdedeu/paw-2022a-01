package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class OfferJdbcDao implements OfferDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Offer.Builder> OFFER_ROW_MAPPER =
            (resultSet, i) -> {
                User seller = User.builder()
                        .id(resultSet.getInt("seller_id"))
                        .email(resultSet.getString("email"))
                        .build();

                OfferStatus offerStatus = OfferStatus.getInstance(
                        resultSet.getString("status_code"),
                        resultSet.getString("status_description")
                );

                String cryptoId = resultSet.getString("crypto_code");
                Cryptocurrency crypto = Cryptocurrency.getInstance(
                        cryptoId,
                        resultSet.getString("commercial_name"),
                        resultSet.getDouble("market_price")
                );

                String paymentCode = resultSet.getString("payment_code");
                PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance( paymentCode, resultSet.getString("payment_description"));

                return Offer.builder(
                                seller,
                                crypto,
                                resultSet.getDouble("asking_price")
                        )
                        .id(resultSet.getInt("offer_id"))
                        .amount(resultSet.getDouble("quantity"))
                        .paymentMethod(pm)
                        .date(resultSet.getTimestamp("offer_date").toLocalDateTime())
                        .status(offerStatus);
            };

    private final static ResultSetExtractor<List<Offer.Builder>> OFFER_MULTIROW_MAPPER = resultSet -> {
        int i = 0;
        Map<Integer, Offer.Builder> cache = new HashMap<>();
        while (resultSet.next()) {
            int offerId = resultSet.getInt("offer_id");
            String paymentCode = resultSet.getString("payment_code");  // TODO: Improve
            PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance( paymentCode, resultSet.getString("payment_description"));
            Offer.Builder instance = cache.getOrDefault(
                    offerId,
                    OFFER_ROW_MAPPER.mapRow(resultSet, i)
            ).paymentMethod(pm);
            cache.putIfAbsent(offerId, instance);
            i ++;
        }
        return cache.values().stream().collect(Collectors.toList());
    };

    @Autowired
    public OfferJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offer").usingGeneratedKeyColumns("offer_id");
    }



    @Override
    public int getOfferCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM offer", Integer.class);
    }


    // Generates a string of the form "AND ( [ nameInDatabase = ? OR]^n FALSE )
    private static String pumpFilterWildcard(int filterQuantity, String nameInDatabase){
        if ( filterQuantity == 0 ) return "";
        StringBuilder sb = new StringBuilder().append("AND (");  // We use a StringBuilder because there may be many iterations
        while ( filterQuantity-- > 0) {
            sb.append(nameInDatabase).append(" = ? OR ");
        }
        sb.append(" FALSE) ");
        return sb.toString();
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {

        int offset = filter.getPageSize() * filter.getPage();

        List<Object> params = new LinkedList<>();

        // %s is to be replaced with appropriate content for theWHERE clause
        final String queryTemplate = "SELECT * FROM offer_complete WHERE offer_id IN (SELECT DISTINCT offer_id FROM offer_complete WHERE TRUE %s LIMIT ? OFFSET ?)";
        String filterSyntax = new StringBuilder() // Here we use StringBuilder because each appended string may be very long
                .append(pumpFilterWildcard(filter.getCryptoCodes().size(), "crypto_code"))
                .append(pumpFilterWildcard(filter.getPaymentMethods().size(), "payment_code"))
                .append(pumpFilterWildcard(filter.getIds().size(), "offer_id"))
                .toString();

        params.addAll(filter.getCryptoCodes());
        params.addAll(filter.getPaymentMethods());
        params.addAll(filter.getIds());
        params.add(filter.getPageSize());
        params.add(offset);

        return jdbcTemplate.query(String.format(queryTemplate, filterSyntax), OFFER_MULTIROW_MAPPER, params.toArray())
                .stream()
                .map(Offer.Builder::build)
                .collect(Collectors.toList());
    }
}

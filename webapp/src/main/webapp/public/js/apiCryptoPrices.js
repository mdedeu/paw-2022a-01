// "exchange":{str} Proveedor a consultar.
// "coin":{str} Cripto a operar.
// "fiat":{str} Moneda contra la que se opera, puede ser fiat o stablecoin.
// "volumen":{float} Volumen a operar, utilizar el punto como separador decimal.

let baseUrl = 'https://criptoya.com/api/tiendacrypto/';
let fiat = '/ars';

function getCryptoPrice(ticker) {

    let url = baseUrl + ticker + fiat;

    return fetch(url, {
        method: 'GET',
    }).then(res => res.json())
        .then(async response => {
            return (await response.totalAsk + await response.totalBid) / 2
        })
        .catch(error => console.error('Error:', error));


}

let btcPrice  =   getCryptoPrice('btc');
let ethPrice  =   getCryptoPrice('eth');
let usdtPrice =  getCryptoPrice('usdt');
let daiPrice  =   getCryptoPrice('dai');


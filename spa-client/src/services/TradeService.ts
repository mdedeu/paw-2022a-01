import { paths } from "../common/constants";
import { AxiosInstance } from "axios";
import Result from "../types/Result";
import TransactionModel from "../types/TransactionModel";

export class TradeService {

    private readonly basePath = paths.BASE_URL + paths.TRADE;
    private readonly axiosInstance : AxiosInstance;

    public constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
    }
    public async getTradeInformation(tradeId: number):Promise<Result<TransactionModel>> {
            const resp = await this.axiosInstance.get<TransactionModel>(this.basePath, {
                params: {
                    tradeId: tradeId
                }
            });
            return Result.ok(resp.data);
    }
}
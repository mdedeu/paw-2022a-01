import {KycService} from "../services/KycService";
import axios from "axios";
import {paths} from "../common/constants";
import * as utils from "../common/utils/utils";


jest.mock('axios')

const username = "salvaCasta"
const kyc_mime = "application/vnd.cryptuki.v1.kyc+json"
const user_list_mime = "application/vnd.cryptuki.v1.user-list+json"

beforeEach(() => {
    axios.get = jest.fn().mockResolvedValue({data: []})
    axios.post = jest.fn().mockResolvedValue({data: []})
    axios.patch = jest.fn().mockResolvedValue({data: []})
})

const solvedKyc = {
    comments: "Kyc comments",
    status: "APR"
}

test("get kyc information", () =>{
    const kycService = new KycService(() => axios)

    kycService.getKycInformation(username)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.USERS + username + "/kyc", {
        "headers": {
            "Accept": kyc_mime
        }
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get kyc pending information by url", () =>{
    const kycService = new KycService(() => axios);
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();
    const test_url = "test_url"

    kycService.getPendingKycInformationByUrl(test_url)

    expect(axios.get).toHaveBeenCalledWith(test_url, {
        "headers": {
            "Accept": user_list_mime
        }
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get kyc pending information", () =>{
    const kycService = new KycService(() => axios);
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();
    let params = new URLSearchParams();
    params.append("kyc_status","PEN");
    params.append("per_page", "2");

    kycService.getPendingKycInformation()

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + "/users", {
        "headers": {
            "Accept": user_list_mime
        },
        "params": params
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("solve kyc", () => {
    const kycService = new KycService(() => axios)

    kycService.solveKyc(solvedKyc, username)

    expect(axios.patch).toHaveBeenCalledWith(paths.BASE_URL + paths.USERS + username + "/kyc", solvedKyc, {
        "headers": {
            "Accept": kyc_mime,
            "Content-Type": kyc_mime
        }
    })
    expect(axios.patch).toHaveBeenCalledTimes(1)
})

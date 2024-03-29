import {ComplainService} from "../services/ComplainService";
import axios from "axios";
import * as utils from "../common/utils/utils";
import {paths} from "../common/constants";
import {CreateComplainForm} from "../views/Support";
import {SolveComplaintFormModel} from "../components/SolveComplaintForm/SolveComplaintForm";

jest.mock('axios')

const test_url = "test_url"

const complaint_resolution_mime = "application/vnd.cryptuki.v1.complaint-resolution+json"
const complaint_mime = "application/vnd.cryptuki.v1.complaint+json"
const complaint_list_mime = "application/vnd.cryptuki.v1.complaint-list+json"

const complainForm : CreateComplainForm = {
    email : "scastagnino@itba.edu.ar",
    tradeId : 1,
    message : "This is a new complaint"
}
const resolutionForm : SolveComplaintFormModel = {
    comments : "These are comments",
    resolution : "This es a resolution",
    complainId : 1
}

beforeEach(() => {
    axios.get = jest.fn().mockResolvedValue({data: []})
    axios.post = jest.fn().mockResolvedValue({data: []})
})

test("get complaints by URL", () => {
    const complainService = new ComplainService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    complainService.getComplaintsByUrl(test_url)

    expect(axios.get).toHaveBeenCalledWith(test_url, {
        "headers": {
            "Accept": complaint_list_mime
        }
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get complaints", () => {
    const complainService = new ComplainService(() => axios)
    jest.spyOn(utils, 'processPaginatedResults').mockReturnThis();

    let params = new URLSearchParams()
    params.append("status","PENDING");

    complainService.getComplaints()

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.COMPLAINTS, {
        "headers": {
            "Accept": complaint_list_mime
        },
        "params": params
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("get complaints by id", () => {
    const complainService = new ComplainService(() => axios)
    axios.get = jest.fn().mockResolvedValue({data: []})

    complainService.getComplaintById(1)

    expect(axios.get).toHaveBeenCalledWith(paths.BASE_URL + paths.COMPLAINTS + "/1", {
        "headers": {
            "Accept": complaint_mime
        }
    })
    expect(axios.get).toHaveBeenCalledTimes(1)
})

test("create complaint", () => {
    const complaintService = new ComplainService(() => axios)

    complaintService.createComplain(complainForm)

    expect(axios.post).toHaveBeenCalledWith(
        paths.BASE_URL + paths.COMPLAINTS,
        {
            tradeId : complainForm.tradeId,
            message : complainForm.message
        },
        {
            "headers": {
                "Accept": complaint_mime,
                "Content-Type": complaint_mime
            }
        }
    )
    expect(axios.post).toHaveBeenCalledTimes(1)
})

test("create complaint resolution", () => {
    const complaintService = new ComplainService(() => axios)

    complaintService.createComplainResolution(resolutionForm)

    expect(axios.post).toHaveBeenCalledWith(
        paths.BASE_URL + paths.COMPLAINTS + "/1/resolution",
        {
            resolution : resolutionForm.resolution,
            comments : resolutionForm.comments
        },{
            "headers": {
                "Accept": complaint_resolution_mime,
                "Content-Type": complaint_resolution_mime
            }
        }
    )
    expect(axios.post).toHaveBeenCalledTimes(1)
})


import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
import api from '@/api/api'

// 获取校友合作列表
export const getCooperationList = (params) => {
    return getAction(api.getCooperationList, params)
}

//获取校友组织列表
export const getAlumnusList = (params) => {
	return getAction(api.getAlumnusList, params)
}




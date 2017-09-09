import Immutable from 'immutable';
import {REQUEST_POSTS, RECEIVE_POSTS, GET_DATA_SUCCESS, GET_REAL_TIME_DATA} from '../action/index';

const defaultlState = Immutable.fromJS({data: {}, isFetching: false});

//首次渲染时获取数据
export const fetchData = (state = defaultlState , action = {}) => {
    switch(action.type){
        case REQUEST_POSTS:
            console.log('Reducer:' + state);
            return state.set('isFetching',true);
        case RECEIVE_POSTS:
            return Immutable.Map({'data':action.json,'isFetching':false});//返回一个新的state
        default:
            return state;
    }
}

// 手动获取数据
export const requestData = (state = {}, action = {}) => {
	switch(action.type) {
		case GET_DATA_SUCCESS:
		    //console.log('GET_DATA_SUCCESS:');
		    //console.log(action.json);
			action.success(action.json);
			//console.log('AFTER success action:');
			console.log(action.json);
			state[action.name] = action.json;
			//console.log('state:');
			//console.log(state);
			return state;
		default:
			return state;
	}
}

export const realTimeData = (state = {}, action = {}) =>{
    switch(action.type) {
        case GET_REAL_TIME_DATA:

            //console.log('get User Data');
            return state;
        default:
            return state;
    }
}


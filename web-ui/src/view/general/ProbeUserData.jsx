import React, { Component, PropTypes } from 'react'; // 引入了React和PropTypes
import { connect } from 'react-redux';
import { Link } from 'react-router';
import { is, fromJS } from 'immutable';
import { RenderData } from '../../component/mixin';
import { Icon, Row, Col, Card, Button, Radio, Table } from 'antd';
const ButtonGroup = Button.Group;

// 公共面包屑
import { Bcrumb } from '../../component/bcrumb/bcrumb';

/* 以类的方式创建一个组件 */
class Main extends Component {
    constructor(props) {
    	super(props);
    	this.state = {
    		userDataSource:[],
            selectedRowKeys: [],
            loading: true
    	};
    }
    onSelectChange = (selectedRowKeys) => {
        console.log('selectedRowKeys changed: ', selectedRowKeys);
        this.setState({ selectedRowKeys });
    };
    componentDidMount(){
    	this.getProbeUserData()
	};
    getProbeUserData = () =>{
        let userName = localStorage.getItem("USERNAME"),
            loginParams = {
                userName: userName
            };
        this.props.getData('queryUserShop.action', loginParams, (res) => {
            console.log(res);
            if(res !== ""){
                this.handleProbeUserData(res)
            }
        })
	};
	handleProbeUserData =(value) => {
        this.state.userDataSource = value.map((item) => {
            return {
                key: item.shopId + Math.random(),
                shop_id: item.shopId,
				userMac: item.mac,
				brand: item.brand,
				stay_time: (item.stayTime / 1000).toFixed(0),
				visit_cycle: item.visitCycle,
				shop_name: item.shopName,
                first_time: item.firstTime,
                recent_time:item.recent_time
            }
        });
        console.log(this.state.userDataSource);
        this.setState({userDataSource: this.state.userDataSource,
                        loading: false});
    };
	render() {
    	const columns=[
            {title:'商场ID', key: 'shop_id', dataIndex:'shop_id', filters:
				[{text:'1', value:'1'},
                    {text:'2', value:'2'},
					{text:'3', value:'3'},
					{text:'4', value:'4'}],
                filterMultiple: true,
                onFilter: (value, record) => record.shop_id.indexOf(value) === 0},
			{title: '商场名称', key:'shop_name', dataIndex: 'shop_name'},
    		{title:'用户MAC', key:'userMac', dataIndex:'userMac'},
			{title:'品牌', key:'brand', dataIndex:'brand', filters:
				[{text: 'Apple', value:'Apple'},
					{text:'HUAWEI', value:'HUAWEI'},
					{text:'Xiaomi', value:'Xiaomi'},
					{text:'Meizu', value:'Meizu'},
					{text:'OPPO', value:'GUANGDONG OPPO'},
					{text:'Samsung', value:'Samsung'},
                    {
                        text: 'More',
                        value: 'More',
                        children: [{
                            text: 'IBM',
                            value: 'IBM',
                        }, {
                            text: 'Motorola',
                            value: 'Motorola',
                        },{
                        	text:'Nokia',
							value:'Nokia'
						},{
                        	text:'Microsoft',
							value:'Microsoft'
						},{
                        	text:'Smartisan',
							value:'Smartisan',
						},{
                            text:'Vivo',
                            value:'Vivo',
                        },{
                            text:'AMPAK',
                            value:'AMPAK',
                        },{
                            text:'Tenda',
                            value:'Tenda',
                        },{
                            text:'Senient',
                            value:'Senient',
                        }],
                    }],
                filterMultiple: true,
                onFilter: (value, record) => record.brand.indexOf(value) === 0,
                sorter: (a, b) => a.brand.length - b.brand.length,},
			{title:'累计停留时间', key: 'stay_time', dataIndex:'stay_time', sorter:(a, b) =>a.stay_time - b.stay_time},
			{title:'来访周期', key:'visit_cycle', dataIndex:'visit_cycle', sorter:(a, b) =>a.visit_cycle - b.visit_cycle},
            {title:'首次出现时间', key:'first_time', dataIndex:'first_time'},
            {title:'最近出现时间', key:'recent_time', dataIndex:'recent_time'}
		];
    	const data=this.state.userDataSource;
        const { loading, selectedRowKeys } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
    	return(
    	<div>
			<Bcrumb title="探针数据"/>
			<Table rowSelection={rowSelection}
				   bordered
				   columns={columns}
				   dataSource={data}
			     />
			<Button className="mg-right10"
				type="primary"
				onClick={this.getProbeUserData}
				loading={loading}
			>
				刷新
			</Button>
		</div>
		)

	}
}

export default RenderData({
	id: 'button', // 应用关联使用的redex
	component: Main // 接收数据的组件入口
});


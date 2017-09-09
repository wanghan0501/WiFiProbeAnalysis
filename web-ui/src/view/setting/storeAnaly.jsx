import React, { Component, PropTypes } from 'react';
import echarts from 'echarts';
import { connect } from 'react-redux';
import { is, fromJS } from 'immutable';
import { RenderData } from '../../component/mixin';
import {Bcrumb} from "../../component/bcrumb/bcrumb";
import { Icon, Row, Col, Card, Button, Radio, Table } from 'antd';
let avgDailyUser;
let backColor = '#404a59';
/* 以类的方式创建一个组件 */
class Main extends Component {
    constructor(props) {
    	super(props);
    }
    shouldComponentUpdate(nextProps, nextState) {
        return !is(fromJS(this.props), fromJS(nextProps)) || !is(fromJS(this.state),fromJS(nextState))
    }
    drawAvgDailyUsers(){

        let option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            title: {
                text: '预测未来一天内流量情况',
                left:'center',
                textStyle: {
                    color: '#fff'
                }
            },
            backgroundColor:backColor,

            legend: {
                data:['客流量','入店量'],
                left:'left',
                textStyle:{
                    color:'#fff'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {   left:'center',
                    top:'center',
                    type : 'category',
                    data : ['00：00','01：00','02：00','03：00','04：00','05：00','06：00','07：00','08：00','09：00','10：00','11：00','12：00','13：00',
                        '14：00','15：00','16：00','17：00','18：00','19：00','20：00','21：00','2：00','23：00','24：00'],
                    axisLine:{
                        lineStyle:{
                            color:'#fff'
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLine:{
                        lineStyle:{
                            color:'#fff'
                        }
                    }
                }
            ],
            series : [
                {
                    label:{
                        normal:{
                            show: true,
                            position:'inside'
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#83bff6'},
                                    {offset: 0.5, color: '#188df0'},
                                    {offset: 1, color: '#00BFFF'}
                                ]
                            )
                        }
                    },
                    name:'客流量',
                    type:'bar',
                    data:[0,0,3,0,10,15,26,60,80,52,79,94,96,96,97,95,98,94,89,75,43,20,15,9]
                },
                {
                    label:{
                        normal:{
                            show: true,
                            position:'inside'
                        }
                    },
                    name:'入店量',
                    type:'bar',
                    stack: '广告',
                    data:[0,0,0,0,5,12,12,34,29,25,40,51,39,48,47,20,36,39,27,21,19,10,8,4]
                }
            ]
        };
        avgDailyUser.setOption(option);
        window.onresize = function () {
            this.autoResize();
            avgDailyUser.resize();
        }.bind(this);
    }
    componentDidMount(){
        this.autoResize();
        avgDailyUser = echarts.init(document.getElementById('avgDailyUser'));
        this.drawAvgDailyUsers();
	}
    autoResize(){
        let avgDailyUser = document.getElementById('avgDailyUser');
        avgDailyUser.style.height = '80vh';
        avgDailyUser.style.width = '100%';
    }

	render() {
		return (	
		<div className="mg-top10">
            <Row>
			    <Bcrumb title="智能决策"/>
                <Col span={24}>
                    <Card title="avgDailyUser" id="avgDailyUser">

                    </Card>
                </Col>
            </Row>
		</div>
		);
	}
}

Main.contextTypes = {

};

export default RenderData({
	id: 'setting', // 应用关联使用的redex
	component: Main // 接收数据的组件入口
});
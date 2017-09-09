import React, { Component, PropTypes } from 'react'; // 引入了React和PropTypes
import { connect } from 'react-redux';
import { is, fromJS } from 'immutable';
import { RenderData } from '../../component/mixin';
import { Icon, Row, Col, Card, Button, Radio, Table, DatePicker} from 'antd';
import moment from 'moment';
import {Bcrumb} from "../../component/bcrumb/bcrumb";
import echarts from 'echarts';
import BrandRatio from "../../component/charts/brandRatio";
/* 以类的方式创建一个组件 */
const RadioButton = Radio.Button;
const RadioGroup = Radio.Group;
let echartDom, customRatio, stayTime, monthlyFlow, dailyUser;
let backColor = '#404a59';

class Main extends Component {
    constructor(props) {
    	super(props);
        this.state = {
            selectedRowKeys: [],  // Check here to configure the default column
            loading: false,
            shopName:[{"shop_id":'1',"shop_name":'南京国际会展中心'},{"shop_id":'2',"shop_name":'华联'},{"shop_id":'3',"shop_name":'二基楼'}],
            yearData:[],
            dayData:[{"checkInnum":4,"hour":1,"number":8},{"checkInnum":5,"hour":2,"number":6},{"checkInnum":1,"hour":3,"number":4},{"checkInnum":2,"hour":4,"number":4},{"checkInnum":0,"hour":5,"number":1},{"checkInnum":7,"hour":6,"number":10},{"checkInnum":3,"hour":7,"number":12},{"checkInnum":10,"hour":8,"number":12},{"checkInnum":1,"hour":9,"number":21},{"checkInnum":12,"hour":10,"number":17},{"checkInnum":4,"hour":11,"number":14},{"checkInnum":5,"hour":12,"number":17},{"checkInnum":1,"hour":13,"number":17},{"checkInnum":4,"hour":14,"number":27},{"checkInnum":12,"hour":15,"number":26},{"checkInnum":22,"hour":16,"number":27},{"checkInnum":6,"hour":17,"number":18},{"checkInnum":7,"hour":18,"number":18},{"checkInnum":13,"hour":19,"number":14},{"checkInnum":3,"hour":20,"number":24},{"checkInnum":15,"hour":21,"number":19},{"checkInnum":8,"hour":22,"number":10},{"checkInnum":5,"hour":23,"number":8},{"checkInnum":0,"hour":24,"number":1}],
            monthData:[]
        };
    }
    shouldComponentUpdate(nextProps, nextState) {
        return !is(fromJS(this.props), fromJS(nextProps)) || !is(fromJS(this.state),fromJS(nextState))
    }
    getShopName(){
        let userName = localStorage.getItem("USERNAME"),
            loginParams = {
                userName: userName
            };
        this.props.getData('queryShopInfos.action', loginParams, (res) => {
            console.log(res);
            if(res !== ""){
                this.state.shopName = res;
                this.setState({shopName: this.state.shopName});
            }
        })
    }

    autoResize(){
        let dom = document.getElementById('CalendarView');
        dom.style.width='100%';
        dom.style.height='400px';


        dom= document.getElementById('stayTime');
        dom.style.width='100%';
        dom.style.height='400px';

        dom= document.getElementById('monthlyFlow');
        dom.style.width='100%';
        dom.style.height='400px';

        dom= document.getElementById('dailyUser');
        dom.style.width='100%';
        dom.style.height='400px';

    }

    getVirtulData(year) {

        //customRatio = echarts.init(document.getElementById('customRatio'));
        year = year || '2017';
        var date = +echarts.number.parseDate(year + '-01-01');
        var end = +echarts.number.parseDate((+year + 1) + '-01-01');
        var dayTime = 3600 * 24 * 1000;
        console.log("date" +date);
        console.log("end" + end);
        var data = [];
        for (var time = date; time < end; time += dayTime) {
                data.push([
                echarts.format.formatTime('yyyy-MM-dd', time),
                Math.floor(Math.random() * 10000)
            ]);
            }
        console.log(data);
        //return data;
        this.state.yearData = data;
        this.setState({yearData: this.state.yearData});
    }

    drawCustomerRatio(){
        let option = {
            backgroundColor: backColor,
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            title: {
                text: '手机品牌',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#ccc'
                }
            },

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 80,
                max: 600,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series : [
                {
                    name:'手机品牌',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:[
                        {value:335, name:'华为'},
                        {value:310, name:'小米'},
                        {value:274, name:'OPPO'},
                        {value:235, name:'IPHONE'},
                        {value:400, name:'其它'}
                    ].sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'area',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#00BFFF',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };
        customRatio.setOption(option);
    }

    drawDataView(){
        const mgLeft = 100;
        const mgTop = 10;
        let data = this.state.yearData;
        //console.log(data);
        let option = {
            backgroundColor: backColor,
            title: {
                text: '用户活跃年历',
                left:'center',
                textStyle: {
                    color: '#fff'
                }
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            tooltip : {
                trigger: 'item'
            },
            legend: {
                left:'left',
                show: true,
                data:['步数', 'Top 12'],
                textStyle: {
                    color: '#fff'
                }
            },
            calendar: [{
                top: 'center',
                left: 'center',
                range: ['2016-01-01', '2016-12-31'],
                monthLabel: {
                    nameMap: 'cn',
                    textStyle:{
                        color:'#fff'
                    }
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#000',
                        width: 4,
                        type: 'solid'
                    }
                },
                yearLabel: {
                    formatter: '{start}',
                    textStyle: {
                        color: '#fff'
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#323c48',
                        borderWidth: 1,
                        borderColor: '#111'
                    }
                }
            }],
            series : [
                {
                    name: '人流量（/人次）',
                    type: 'scatter',
                    coordinateSystem: 'calendar',
                    data: data,
                    symbolSize: function (val) {
                        return val[1] / 500;
                    },
                    itemStyle: {
                        normal: {
                            color: '#00BFFF'
                        }
                    }
                },
                {
                    name: 'Top 12',
                    type: 'effectScatter',
                    coordinateSystem: 'calendar',
                    data: data.sort(function (a, b) {
                        return b[1] - a[1];
                    }).slice(0, 12),
                    symbolSize: function (val) {
                        return val[1] / 500;
                    },
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    hoverAnimation: true,
                    itemStyle: {
                        normal: {
                            color: '#f4e925',
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    zlevel: 1
                }
            ]
        };

        echartDom.setOption(option);
        window.onresize = function () {
            this.autoResize();
            echartDom.resize();
        }.bind(this);
    }

    drawDailyUsers(){

        let option = {
            backgroundColor: backColor,
            title: {
                text: '平均每天流量分析图',
                textStyle: {
                    fontWeight: 'normal',
                    fontSize: 16,
                    color: '#F1F1F3'
                },
                left: '6%'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    lineStyle: {
                        color: '#57617B'
                    }
                }
            },
            legend: {
                icon: 'rect',
                itemWidth: 14,
                itemHeight: 5,
                itemGap: 13,
                data: ['区域客流量', '入店量'],
                right: '4%',
                textStyle: {
                    fontSize: 12,
                    color: '#F1F1F3'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                axisLine: {
                    lineStyle: {
                        color: '#57617B'
                    }
                },
                data: this.state.dayData.map((item) => {
                    return item.hour;
                }),
            }],
            yAxis: [{
                type: 'value',
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#57617B'
                    }
                },
                axisLabel: {
                    margin: 10,
                    textStyle: {
                        fontSize: 14
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: '#57617B'
                    }
                }
            }],
            series: [{
                name: '区域客流量',
                type: 'line',
                smooth: true,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(137, 189, 27, 0.3)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(137, 189, 27, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgb(137,189,27)'
                    }
                },
                data: this.state.dayData.map((item) => {
                    return item.number;
                }),
            }, {
                name: '入店量',
                type: 'line',
                smooth: true,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(219, 50, 51, 0.3)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(219, 50, 51, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgb(219,50,51)'
                    }
                },
                data: this.state.dayData.map((item) => {
                    return item.checkInnum;
                }),
            }, ]
        };
        dailyUser.setOption(option);
    }

    drawStayTime(){
        let option = {
            backgroundColor: backColor,

            title: {
                text: '来访顾客平均时长占比',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#fff'
                }
            },

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 80,
                max: 600,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series : [
                {
                    name:'访问时长',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:[
                        {value:335, name:'大于3 hour'},
                        {value:310, name:'1 hour -- 3 hour'},
                        {value:274, name:'30 min--1 hour'},
                        {value:235, name:'10 min--30 min'},
                        {value:400, name:'小于 10 min'}
                    ].sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        show:true,
                        position:'inside',
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#00BFFF',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };
        stayTime.setOption(option);
    }

    drawMonthlyFlow(){
        var dataAxis = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
        var data = [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220];
        var yMax = 500;
        var dataShadow = [];

        for (var i = 0; i < data.length; i++) {
            dataShadow.push(yMax);
        }
        let option = {
            title: {
                text: '平均每月人流量',
                left: 'center',
                textStyle:{
                    color:'#fff'
                }
            },
            backgroundColor:backColor,
            xAxis: {
                data: dataAxis,
                axisLabel: {
                    inside: false,
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                z: 10
            },
            yAxis: {
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#999'
                    }
                }
            },
            dataZoom: [
                {
                    type: 'inside'
                }
            ],
            series: [
                { // For shadow
                    type: 'bar',
                    itemStyle: {
                        normal: {color: 'rgba(0,0,0,0.05)'}
                    },
                    barGap:'-100%',
                    barCategoryGap:'40%',
                    data: dataShadow,
                    animation: false
                },
                {
                    type: 'bar',
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
                        },
                        emphasis: {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#2378f7'},
                                    {offset: 0.7, color: '#2378f7'},
                                    {offset: 1, color: '#00BFFF'}
                                ]
                            )
                        }
                    },
                    data: data
                }
            ]
        };
        monthlyFlow.setOption(option);
        // Enable data zoom when user click bar.
        var zoomSize = 6;
        monthlyFlow.on('click', function (params) {
            console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
            monthlyFlow.dispatchAction({
                type: 'dataZoom',
                startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
                endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
            });
        });
    }

    componentDidUpdate(){
        console.log("update");
        this.drawDataView();
    }

    getYearData(){
        this.props.getData("queryActivityYear.action", "", (res) => {
            console.log(res);
            this.setState({yearData: res})
        });
    }
    onMonthChange(){
        this.props.getData("queryActivityMongth.action", "", (res) =>{
           console.log(res);
           this.setState({monthData:res});
        });
    }
    componentDidMount(){
        this.autoResize();
        echartDom = echarts.init(document.getElementById('CalendarView'));
        //customRatio = echarts.init(document.getElementById('customRatio'));
        stayTime = echarts.init(document.getElementById('stayTime'));
        monthlyFlow = echarts.init(document.getElementById('monthlyFlow'));
        dailyUser = echarts.init(document.getElementById('dailyUser'));
        this.drawDataView();
        //this.drawCustomerRatio();
        this.drawStayTime();
        this.drawMonthlyFlow();
        this.drawDailyUsers();

    }
    onDayChange = (date, dateString) => {
        console.log("dateString:");
        console.log(dateString);
        let dateStr = dateString,
        params = {
            activityDay: dateStr
        };
        this.props.getData("queryActivityDay.action", params, (res) => {
            if(res !=="") {
                console.log("success get date");
                console.log(res);
                this.state.DayData = res;
                console.log(this.state.DayData);
                this.setState({dayData: this.state.dayData});
                this.drawDailyUsers();
            }
        }, "date", "GET");
    };
    componentDidUpdate(){
        this.drawDailyUsers();
    }

    render() {
        const columns=[
            {title:'开始时间', dataIndex:'start_time'},
            {title:'截止时间', dataIndex:'end_time'}
        ];
        const { MonthPicker, RangePicker } = DatePicker;
        const dateFormat = 'YYYY/MM/DD';
        const monthFormat = 'YYYY/MM';
		return(
            <div>
                <Row>
                    <Bcrumb title="数据一览"/>
                    <Col span={4}>
                        <div className="mg-left10">
                            <RadioGroup defaultValue="1" size="large" onChange={this.getVirtulData(2016)}>
                                {this.state.shopName.map((shop)=>
                                    <RadioButton key={shop.shop_id} value={shop.shop_id}>{shop.shop_name}</RadioButton>)}
                            </RadioGroup>
                            {/*<Table columns={columns} className="mg-top20"/>*/}
                        </div>
                    </Col>
                    <Col span={20}>
                        <Card title="日历数据" id="CalendarView" className="mg-left10"/>
                    </Col>

                    <Col span={4}>
                        <div className="mg-left10">
                            <MonthPicker onChange={this.onMonthChange} />
                        </div>
                    </Col>
                    <Col span={20}>
                        <Card title="月人流量" id="monthlyFlow" className="mg-left10"/>
                    </Col>

                    <Col span={4}>
                        <div className="mg-left10">
                            <DatePicker onChange={this.onDayChange} />
                        </div>
                    </Col>
                    <Col span={20}>
                        <Card title="每日活跃日志" id="dailyUser" className="mg-left10"/>
                    </Col>

                    <Col span={4}>
                    </Col>
                    <Col span={10}>
                        <div className="mg-left10">
                            <BrandRatio/>
                        </div>
                    </Col>
                    <Col span={10}>
                        <Card title="驻留时长" id="stayTime"/>
                    </Col>
                </Row>
            </div>
        );

	}
}

export default RenderData({
	id: 'icon', // 应用关联使用的redex
	component: Main // 接收数据的组件入口
});


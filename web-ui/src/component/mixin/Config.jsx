const Main = {
	target: process.env.NODE_ENV !== 'production' ? 'http://localhost:8080/' : 'http://120.24.238.195:8080/WIFIProbeAnalysis_web-1.0-SNAPSHOT/', //目标网站
    name: 'WIFI Probe Admin',
    prefix: 'SCUNET Admin',
    footerText: '川行 版权所有 © 2017 All Rights Reserved',
    logoSrc: 'https://t.alipayobjects.com/images/rmsweb/T1B9hfXcdvXXXXXXXX.svg',
    logoText: 'SCUNET Admin',
    needLogin: true,
	message: { // 提示信息
		usernameInput: '请输入用户名',
        usernameEng: '用户名必须是字母',
		verifyCodeNum: '验证码只能是数字',
		usernameTel: '用户名必须是手机号',
		passwordInput: '请输入密码',
		verifyCodeInput: '请输入验证码',
		loginError: '请检查用户名、密码和验证码!',
		shopNameInput: '请输入商场名称'
	},
	localKey: { // 本地存储Key
		userToken: 'USER_AUTHORIZATION',
		userName: 'USERNAME'
	},
	/**
	 * 只能输入英文
	 * 
	 * @param {any} str
	 * @returns
	 */
	checkEng(str) {
		var reg = new RegExp(/^[A-Za-z]+$/);
		return str && reg.test(str);
	},
    /**
     * 只能输入手机号
     *
     * @param {any} str
     * @returns
     */
    checkTelephone(str) {
        var reg = new RegExp(/^1[3|5|7|8][0-9]\d{4,8}$/);
        return str && reg.test(str);
    },
    /**
     * 只能输入数字
     *
     * @param {any} str
     * @returns
     */
    checkNumber(str) {
        var reg = new RegExp("^[0-9]*$");
        return str && reg.test(str);
    },
    /**
	 * 参数格式化
	 * 
	 * @param {any} data
	 * @returns
	 */
	paramFormat(data) {
		let paramArr = [];
		let paramStr = '';
		for(let attr in data) {
			paramArr.push(attr + '=' + data[attr]);
		}
		paramStr = paramArr.join('&');
		return paramStr ? '?' + paramStr : paramStr;
	},
    /**
	 * 本地数据存储或读取
	 * 
	 * @param {any} key
	 * @param {any} value
	 * @returns
	 */
	localItem(key, value) {
		if(arguments.length == 1) {
			console.log('key' + key, ', value:'+value);
			return localStorage.getItem(key) && localStorage.getItem(key) !== 'null' ? localStorage.getItem(key) : null;
		} else {
            console.log('key' + key, ',value:' + value);
			return localStorage.setItem(key, value);
		}
	},
	/**
	 * 删除本地数据
	 * 
	 * @param {any} k
	 * @returns
	 */
	removeLocalItem(key) {
		if(arguments.length === 1) {
			return localStorage.removeItem(key);
		} else {
			return localStorage.clear();
		}
	}
};

export default Main;

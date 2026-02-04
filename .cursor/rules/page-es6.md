// ✅ 使用 ES6+ 语法
class HomePage {
    constructor() {
        this.data = {
            userInfo: {},
            listData: []
        };
    }

    // ✅ 使用 async/await 处理异步
    async onLoad() {
        try {
            await this.getUserInfo();
            await this.loadData();
        } catch (error) {
            this.handleError(error);
        }
    }

    // ✅ 方法使用驼峰命名
    getUserInfo() {
        return new Promise((resolve, reject) => {
            wx.getUserProfile({
                desc: '用于完善会员资料',
                success: (res) => {
                    this.setData({ userInfo: res.userInfo });
                    resolve(res.userInfo);
                },
                fail: reject
            });
        });
    }

    // ✅ 事件处理统一前缀
    handleItemTap(event) {
        const { id } = event.currentTarget.dataset;
        wx.navigateTo({
            url: `/pages/detail/detail?id=${id}`
        });
    }

    // ✅ 私有方法使用下划线前缀
    _formatData(data) {
        return data.map(item => ({
            ...item,
            createTime: this._formatTime(item.createTime)
        }));
    }
}
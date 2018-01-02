ELEMENT.locale(ELEMENT.lang.ja)
var app = new Vue({
    el: '#app',
    data: {
        ok: false,
        error: false,
        nodeForm: {
            nodes: []
        },
        mineForm: {
            balance: 0,
            mine: false,
            logs: ''
        }
    },
    methods: {
        async mining() {
            var that = this;

            if (!that.mineForm.mine) {
                return;
            }
            that.mineForm.logs = '*** Mining Start ***\n';

            while (that.mineForm.mine) {
                // mining
                const mineResponse = await axios.get('/mine');
                var uuid = mineResponse.data;

                if (mineResponse.status !== 200) {
                    that.mineForm.logs = moment(new Date).format('HH:mm:ss') + ' Mining:Error!\n' + that.mineForm.logs;
                } else {
                    that.mineForm.logs = moment(new Date).format('HH:mm:ss') + ' Mining:Find hash!\n' + that.mineForm.logs;
                }

                // resolve chain
                const resolveResponse = await axios.get('/node/resolve');

                if(resolveResponse.status !== 200) {
                    that.mineForm.logs = moment(new Date).format('HH:mm:ss') + ' Resolve:Error!\n' + that.mineForm.logs;
                } else {
                    if(resolveResponse.data) {
                        that.mineForm.logs = moment(new Date).format('HH:mm:ss') + ' Resolve:Replaced\n' + that.mineForm.logs;
                    } else {
                        that.mineForm.logs = moment(new Date).format('HH:mm:ss') + ' Resolve:Registered\n' + that.mineForm.logs;
                    }
                }

                // refresh wallet
                await axios.post('/wallet', {
                    uuid: uuid
                }).then(response => {
                    that.mineForm.balance = response.data;
                }).catch(error => {
                    that.mineForm.balance = 0;
                });
            }
        },
        regist() {
            var that = this;
            axios.post('/node/register', {
                nodes: that.nodeForm.nodes
            }).then(response => {
                that.ok = true;
                that.error = false;
            }).catch(error => {
                that.ok = false;
                that.error = true;
            });
        }
    }
})
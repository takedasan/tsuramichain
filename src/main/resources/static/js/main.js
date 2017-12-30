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
                const res = await axios.get('/mine');

                if (res.status !== 200) {
                    that.mineForm.logs = moment(new Date).format('HH:mm:ss') + ' Error!\n' + that.mineForm.logs;
                } else {
                    that.mineForm.logs = moment(new Date).format('HH:mm:ss') + ' Get 1 tsurami coin\n' + that.mineForm.logs;
                }
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
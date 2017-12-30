package jp.takeda.tsuramichain.app.node.register

data class RegisterForm(
        var hoge: String = "",
        var nodes: MutableList<String> = mutableListOf()
)
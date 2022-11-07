package com.gmbuyi.temno.api.models

import java.math.BigDecimal


class Account(

    val account_id : Long,
    val user : User,
    val balance : BigDecimal
)

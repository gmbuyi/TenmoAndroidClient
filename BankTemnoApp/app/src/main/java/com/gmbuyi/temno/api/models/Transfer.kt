package com.gmbuyi.temno.api.models

import java.math.BigDecimal

class Transfer
    (
      val transferId: Long,
      val fromAccount : Account,
      val toAccount : Account,
      val transferStatus: TransferStatus,
      val typeTransfer: TransferType,
      val amountForTransfer: BigDecimal
            )
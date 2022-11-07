package com.gmbuyi.temno.api.models

class TransferStatus(
    var transferStatusId: Int,
    var transferStatusDesc: String
){

  companion object{

      const val STATUS_APPROVE = 2
      const val STATUS_REJECT = 3
      const val STATUS_PENDING = 1
      const val DESC_APPROVE = "Approved"
      const val DESC_REJECT = "Rejected"
      const val DESC_PENDING = "Pending"
  }


}

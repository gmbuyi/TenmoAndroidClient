package com.gmbuyi.temno.api.models

class TransferType(
     val transferTypeId : Int,
     val transferTypeDesc: String
     )
{
     companion object{

          const val ID_SEND = 2
          const val ID_REQUEST = 1
          const val DESC_SEND = "Send"
          const val DESC_REQUEST = "Request"
     }


}
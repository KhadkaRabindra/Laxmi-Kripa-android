package com.maxx.eparchi.model

class HomeData(var time : String, var location : String, var date : String, var month : String)

class LaminationData{
    var title : String? = null
    var showList : Boolean? = null
    var crucibleList: ArrayList<Crucible>? = null
}

class Crucible{
    var id : String? = null
    var isOK : Boolean? = null
    var reasonList : ArrayList<Reason>? = null
    var selection : Int? = null
}

class Reason(
    var name: String? = null
)

/*Forms*/
class FirstFormData{
    var id: String? = null
    var startTime : String? = null
    var operator : String? = null
    var helper : String? = null
    var companyController: String? = null
    var wireType : String? = null
}


class FourthFormData{
    var id: String? = null
    var endTime : String? = null
    var startTime : String? = null
    var totalBundles : String? = null
}
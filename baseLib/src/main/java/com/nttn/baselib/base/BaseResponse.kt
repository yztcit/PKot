package com.nttn.baselib.base

class BaseResponse<T>(val code: Int, val msg: String, val data: T)
package com.nttn.pkot.base

class BaseResponse<T>(val code: Int, val msg: String, val data: T)
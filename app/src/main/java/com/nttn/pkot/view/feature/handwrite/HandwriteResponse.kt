package com.nttn.pkot.view.feature.handwrite

data class HandwriteResponse(
    val error_code: Int? = 0, val error_msg: String? = null,
    val angle: Int,//角度，
    val prism_tablesInfo: List<PrismTablesInfo>?,//表格信息，如果不存在表格，则该字段内容为空
    val prism_version: String,//算法版本
    val prism_wnum: Int, //识别的文字块的数量，prism_wordsInfo数组大小
    val prism_wordsInfo: List<PrismWordsInfo>,//识别的文字的具体内容
    val sid: String //唯一id，用于问题定位
)

data class PrismTablesInfo(
    val cellInfos: List<CellInfo>,//单元格信息，包含单元格在整个表格中的空间拓扑关系
    val tableId: Int,//表格id，和prism_wordsInfo信息中的tableId对应
    val xCellSize: Int,  //表格中横坐标单元格的数量
    val yCellSize: Int  //表格中纵坐标单元格的数量
)

data class PrismWordsInfo(
    val charInfo: List<CharInfo>, //单字信息
    //文字块的位置，按照文字块四个角的坐标顺时针排列，分别为左上XY坐标、右上XY坐标、右下XY坐标、左下XY坐标
    val pos: List<PoX>,
    val prob: Int, //置信度
    val tableCellId: Int,  //如果该文字块在表格内则存在该字段，tableId表示表格的id
    val tableId: Int,  //如果该文字块在表格内则存在该字段，tableId表示表格的id
    val word: String  //文字块
)

data class CellInfo(
    //单元格位置，按照单元格四个角的坐标顺时针排列，分别为左上XY坐标、右上XY坐标、右下XY坐标、左下XY坐标
    val pos: List<Po>,
    val tableCellId: Int, //表格中单元格id，和prism_wordsInfo信息中的tableCellId对应
    val word: String, //单元格中的文字
    //xEndCell缩写，表示横轴方向该单元格结束在第几个单元格，第一个单元格值为0，如果xsc和xec都为0说明该文字在横轴方向占据了一个单元格并且在第一个单元格内
    val xec: Int,
    val xsc: Int, //xStartCell缩写，表示横轴方向该单元格起始在第几个单元格，第一个单元格值为0
    val yec: Int,  //yEndCell缩写，表示纵轴方向该单元格结束在第几个单元格，第一个单元格值为0
    val ysc: Int //yStartCell缩写，表示纵轴方向该单元格起始在第几个单元格，第一个单元格值为0
)

data class Po(
    val x: Int,
    val y: Int
)

data class CharInfo(
    val h: Int,  //单字长度
    val prob: Int,  //单字置信度
    val w: Int, //单字宽度
    val word: String,  //单字文字
    val x: Int,      //单字左上角横坐标
    val y: Int  //单字左上角纵坐标
)

data class PoX(
    val x: Int,
    val y: Int
)

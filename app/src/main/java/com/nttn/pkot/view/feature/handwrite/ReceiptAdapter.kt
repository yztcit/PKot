package com.nttn.pkot.view.feature.handwrite

import com.nttn.baselib.base.AbstractVBAdapter
import com.nttn.pkot.ItemReceiptBinding
import com.nttn.pkot.R

class ReceiptAdapter(receipts: ArrayList<PrismWordsInfo>) :
    AbstractVBAdapter<PrismWordsInfo, ItemReceiptBinding>(receipts,
        listener = { _, _ -> },
        onBind = { bind, position ->
            bind.tvDateTime.text = "2022-04-29 18:25".plus(position)
            bind.tvContent.text = receipts[position].word
        }) {
    override fun getLayoutId(): Int = R.layout.item_receipt
}
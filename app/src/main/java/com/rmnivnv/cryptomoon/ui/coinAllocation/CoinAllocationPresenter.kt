package com.rmnivnv.cryptomoon.ui.coinAllocation

import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.model.PieMaker
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CoinAllocationPresenter @Inject constructor(private val view: ICoinAllocation.View,
                                                  private val pieMaker: PieMaker,
                                                  private val db: CMDatabase): ICoinAllocation.Presenter {

    private val disposable = CompositeDisposable()
    private var coins: ArrayList<DisplayCoin> = ArrayList()

    override fun onCreate(coins: List<DisplayCoin>) {
        addCoinsChangesObservable()
    }

    private fun addCoinsChangesObservable() {
        disposable.add(db.displayCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinsFromDbUpdates(it) }))
    }

    private fun onCoinsFromDbUpdates(list: List<DisplayCoin>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
            coins.sortBy { it.from }
            view.drawPieChart(pieMaker.makeChart(coins))
            view.disableGraphLoading()
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }

}
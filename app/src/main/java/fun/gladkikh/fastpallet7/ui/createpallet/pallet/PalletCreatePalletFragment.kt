package `fun`.gladkikh.fastpallet7.ui.createpallet.pallet

import `fun`.gladkikh.fastpallet7.Constants
import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.common.toSimpleDateTime
import `fun`.gladkikh.fastpallet7.common.toSimpleFormat
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.ui.activity.MainActivity
import `fun`.gladkikh.fastpallet7.ui.base.BaseFragment
import `fun`.gladkikh.fastpallet7.ui.base.MyBaseAdapter
import `fun`.gladkikh.fastpallet7.ui.common.Command
import `fun`.gladkikh.fastpallet7.ui.common.Command.Close
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.block_pallet.*
import kotlinx.android.synthetic.main.block_product.*
import kotlinx.android.synthetic.main.create_pallet_fragment_pallet.*
import kotlinx.android.synthetic.main.list_block.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PalletCreatePalletFragment : BaseFragment() {

    override val layoutRes = R.layout.create_pallet_fragment_pallet
    override val viewModel: PalletCreatePalletViewModel by viewModel()

    private lateinit var adapter: Adapter


    override fun initSubscription() {
        super.initSubscription()

        frameLayoutProduct.visibility = View.GONE

        val guid = ((viewModel.getGuid()) ?: (arguments?.get(Constants.EXTRA_GUID))) as String

        adapter = Adapter(activity as Context)
        listView.adapter = adapter

        viewModel.setGuid(guid)

        viewModel.getProductLiveData().observe(viewLifecycleOwner, Observer {
            renderProduct(it)
        })

        viewModel.getPalletLiveData().observe(viewLifecycleOwner, Observer {
            renderPallet(it)
        })

        viewModel.getListBoxLiveData().observe(viewLifecycleOwner, Observer {
            renderListBox(it)
        })

        mainActivity.barcodeLiveData.observe(viewLifecycleOwner, Observer {
            //viewModel.readBarcode(it)
        })

        listView.setOnItemClickListener { _, _, i, _ ->
            adapter.list[i].countBox?.let {
                (activity as MainActivity).navigateHandler.startCreatePalletBox(
                    adapter.list[i].guid?.let {
                        openBox(it)
                    }
                )
            }
        }


    }

    private fun renderListBox(list: List<BoxCreatePallet>) {
        adapter.list = list
    }

    override fun keyDownListener(keyCode: Int) {
        super.keyDownListener(keyCode)
        //viewModel.keyDown(keyCode)
        when (keyCode) {
            Constants.KEY_5 -> {
                if (frameLayoutProduct.visibility == View.GONE) {
                    frameLayoutProduct.visibility = View.VISIBLE
                } else {
                    frameLayoutProduct.visibility = View.GONE
                }
            }
        }
    }
    override fun commandListener(command: Command) {
        super.commandListener(command)
        when (command) {
            is Close -> {
                navigateHandler.popBackStack()
            }

        }
    }

    fun renderProduct(product: ProductCreatePallet?) {
        tvNameProduct.text = product?.nameProduct ?: ""
        tvCountProduct.text = product?.count.toSimpleFormat()
        tvCountPlaceProduct.text = product?.countBox.toSimpleFormat()
        tvCountPalletProduct.text = product?.countPallet.toSimpleFormat()

        tvCountBackProduct.text = product?.countBack.toSimpleFormat()
        tvCountPlaceBackProduct.text = product?.countBoxBack.toSimpleFormat()
    }

    fun renderPallet(pallet: PalletCreatePallet?) {
        tvNumberPallet.text = pallet?.number ?: ""
        tvCountPallet.text = pallet?.count.toSimpleFormat()
        tvCountPlacePallet.text = pallet?.countBox.toSimpleFormat()
        tvCountRowPallet.text = pallet?.countRow.toSimpleFormat()
    }

    private class Adapter(mContext: Context) : MyBaseAdapter<BoxCreatePallet>(mContext) {
        override fun bindView(item: BoxCreatePallet, holder: Any) {
            holder as ViewHolder
            holder.tvDateBox.text = item.dateChanged.toSimpleDateTime()
            holder.tvCountBox.text = item.count.toSimpleFormat()
            holder.tvCountPlaceBox.text = item.countBox.toSimpleFormat()

        }

        override fun getLayout(): Int = R.layout.item_box
        override fun createViewHolder(view: View): Any =
            ViewHolder(
                view
            )
    }

    private class ViewHolder(view: View) {
        var tvDateBox: TextView = view.findViewById(R.id.tvDateBox)
        var tvCountBox: TextView = view.findViewById(R.id.tvCountBox)
        var tvCountPlaceBox: TextView = view.findViewById(R.id.tvCountPlaceBox)
    }


}
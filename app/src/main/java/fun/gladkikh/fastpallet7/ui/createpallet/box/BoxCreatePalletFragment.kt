package `fun`.gladkikh.fastpallet7.ui.createpallet.box

import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.common.toSimpleDateTime
import `fun`.gladkikh.fastpallet7.common.toSimpleFormat
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.BoxCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.PalletCreatePallet
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.ui.activity.MainActivity
import `fun`.gladkikh.fastpallet7.ui.base.BaseFragment
import `fun`.gladkikh.fastpallet7.ui.base.Command
import `fun`.gladkikh.fastpallet7.ui.base.Command.Close
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.block_box.*
import kotlinx.android.synthetic.main.block_pallet.*
import kotlinx.android.synthetic.main.block_product.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class BoxCreatePalletFragment : BaseFragment() {

    override val layoutRes = R.layout.create_pallet_fragment_box
    override val viewModel: BoxCreatePalletViewModel by viewModel()

    companion object {
        val EXTRA_GUID = this::class.java.name + "extra.GUID"
    }

    override fun initSubscription() {
        super.initSubscription()
        val guid = ((viewModel.box?.guid) ?: (arguments?.get(EXTRA_GUID))) as String

        viewModel.setGuid(guid)

        viewModel.getProductLiveData().observe(viewLifecycleOwner, Observer {
            renderProduct(it)
        })

        viewModel.getPalletLiveData().observe(viewLifecycleOwner, Observer {
            renderPallet(it)
        })

        viewModel.getBoxLiveData().observe(viewLifecycleOwner, Observer {
            renderBox(it)
        })

        tvCountBox.setOnClickListener {
            viewModel.readBarcode("${(10..99).random()}123456789")
        }
    }

    override fun keyDownListener(keyCode: Int) {
        super.keyDownListener(keyCode)
        viewModel.keyDown(keyCode)
    }

    override fun commandListener(command: Command) {
        super.commandListener(command)
        when (command) {
            is Close -> {
                mainActivity.navController.popBackStack()
            }

        }
    }

    fun renderProduct(product: ProductCreatePallet?) {
        tvNameProduct.text = product?.nameProduct ?: ""
        tvCountProduct.text = product?.count.toSimpleFormat()
        tvCountPlaceProduct.text = product?.countBox.toSimpleFormat()
        tvCountPalletProduct.text = product?.countPallet.toSimpleFormat()
        tvCountRowProduct.text = product?.countRow.toSimpleFormat()

        tvCountBackProduct.text = product?.countBack.toSimpleFormat()
        tvCountPlaceBackProduct.text = product?.countBoxBack.toSimpleFormat()
    }

    fun renderPallet(pallet: PalletCreatePallet?) {
        tvNumberPallet.text = pallet?.number ?: ""
        tvCountPallet.text = pallet?.count.toSimpleFormat()
        tvCountPlacePallet.text = pallet?.countBox.toSimpleFormat()
        tvCountRowPallet.text = pallet?.countRow.toSimpleFormat()
    }

    fun renderBox(box: BoxCreatePallet?) {

        tvDateBox.text = box?.dateChanged.toSimpleDateTime()
        tvCountBox.text = box?.count.toSimpleFormat()
        tvCountPlaceBox.text = box?.countBox.toSimpleFormat()

        tvCountBox.startAnimation((activity as MainActivity).fadeInAnim)
        tvCountPlaceBox.startAnimation((activity as MainActivity).fadeInAnim)

    }
}
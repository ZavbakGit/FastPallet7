package `fun`.gladkikh.fastpallet7.ui.createpallet.productdialog

import `fun`.gladkikh.fastpallet7.Constants
import `fun`.gladkikh.fastpallet7.R
import `fun`.gladkikh.fastpallet7.common.getWeightByBarcode
import `fun`.gladkikh.fastpallet7.common.toSimpleFormat
import `fun`.gladkikh.fastpallet7.model.entity.creatpallet.ProductCreatePallet
import `fun`.gladkikh.fastpallet7.ui.base.BaseFragment
import `fun`.gladkikh.fastpallet7.ui.common.Command
import `fun`.gladkikh.fastpallet7.ui.common.Command.Close
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.product_template_weight_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDialogCreatePalletFragment : BaseFragment() {

    override val layoutRes = R.layout.product_template_weight_fragment
    override val viewModel: ProductDialogCreatePalletViewModel by viewModel()

    override fun initSubscription() {
        super.initSubscription()

        val guid = ((viewModel.getGuid()) ?: (arguments?.get(Constants.EXTRA_GUID))) as String

        viewModel.setGuid(guid)

        viewModel.getProductLiveData().observe(viewLifecycleOwner, Observer {
            renderProduct(it)
        })


        tvBarcodeProductDialog.setOnClickListener {
            viewModel.readBarcode("${(10..99).random()}123456789")
        }

        mainActivity.barcodeLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.readBarcode(it)
        })

    }

    override fun keyDownListener(keyCode: Int) {
        super.keyDownListener(keyCode)
        viewModel.keyDown(keyCode)
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
        tvNameProductDialog.text = product?.nameProduct ?: ""

        val barcode = product?.barcode ?: ""
        val start = product?.weightStartProduct ?: 0
        val finish = product?.weightEndProduct ?: 0
        val coff = product?.weightCoffProduct ?: 0f


        tvBarcodeProductDialog.text = barcode
        tvStartProductDialog.text = start.toSimpleFormat()
        tvEndProductDialog.text = finish.toSimpleFormat()
        tvCoffProductDialog.text = coff.toSimpleFormat()

        val weight = getWeightByBarcode(
            barcode = barcode,
            start = start,
            finish = finish,
            coff = coff
        )

        tvWeightProductDialog.text = weight.toSimpleFormat()

    }
}
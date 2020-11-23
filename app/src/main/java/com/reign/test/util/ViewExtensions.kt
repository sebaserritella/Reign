package com.reign.test.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.telephony.PhoneNumberUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.text.parseAsHtml
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.reign.test.R
import java.util.*

/**
 * Setup the visibility of the view depending on boolean value
 */
@BindingAdapter("android:isVisible")
fun View.setIsVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}


/**
 * Converts a dp value to equivalent pixel value
 *
 * @param context
 * @param value The value to apply the conversion
 *
 * @return Pixel value
 */
fun getPixelValue(context: Context, value: Int) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(),
        context.resources.displayMetrics
    ).toInt()

/**
 * Check if permission is granted
 */
fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/**
 * Return Main Navigation Controller
 */
fun Activity.getMainNavController() = findNavController(R.id.hitsFragment)

/**
 * Return Main Navigation Controller
 */
fun Fragment.getMainNavController() = activity?.getMainNavController()
fun Fragment.backToPreviousScreen() = getMainNavController()?.navigateUp()

/**
 * Navigate via directions that describe this navigation operation, trough the main navigation controller
 */
fun Fragment.navigateTo(navDirections: NavDirections) {
    getMainNavController()?.currentDestination?.getAction(navDirections.actionId)?.let {
        getMainNavController()
            ?.navigate(navDirections)
    }
}


/**
 * Return screen width
 */
fun Fragment.getScreenWidth(): Int {
    val wm = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

fun Activity.setStatusBarTransparent() {
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.statusBarColor = Color.TRANSPARENT
}

fun Activity.restoreStatusBarTransparency(flag: Int) {
    window.decorView.systemUiVisibility = flag
}

fun Fragment.setStatusBarColor(color: Int) {
    val window = activity?.window
    window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window?.statusBarColor = resources.getColor(color, null)
}

fun Fragment.showKeyboard(editText: EditText?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.view?.windowToken, 0)
}

fun Fragment.openWebPage(url: String?): Boolean {
    val packageManager = context?.packageManager ?: return false
    val webPage: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webPage)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (intent.resolveActivity(packageManager) != null) {
        context?.startActivity(intent)
        return true
    }
    return false
}

fun Context.getActionBarHeight(): Int {
    val tv = TypedValue()
    return if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true))
        TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    else
        0
}
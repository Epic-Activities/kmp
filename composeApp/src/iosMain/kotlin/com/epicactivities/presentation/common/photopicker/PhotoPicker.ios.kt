package com.epicActivities.presentation.common.photopicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerImageURL
import platform.UIKit.UIImagePickerControllerSourceTypePhotoLibrary
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject

// Retained to prevent GC while the picker is open.
private var activePickerDelegate: NSObject? = null

@Composable
actual fun rememberPhotoPicker(onResult: (uri: String?) -> Unit): () -> Unit = remember {
    {
        @Suppress("DEPRECATION")
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        if (rootViewController != null) {
            val picker = UIImagePickerController()
            picker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary
            picker.allowsEditing = false

            val delegate = object : NSObject(),
                UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {

                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>,
                ) {
                    val url = didFinishPickingMediaWithInfo[UIImagePickerControllerImageURL] as? NSURL
                    picker.dismissViewControllerAnimated(true, completion = null)
                    activePickerDelegate = null
                    onResult(url?.absoluteString)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    picker.dismissViewControllerAnimated(true, completion = null)
                    activePickerDelegate = null
                    onResult(null)
                }
            }

            activePickerDelegate = delegate
            picker.delegate = delegate
            rootViewController.presentViewController(picker, animated = true, completion = null)
        }
    }
}

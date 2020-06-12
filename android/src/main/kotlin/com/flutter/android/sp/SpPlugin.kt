package com.flutter.android.sp

import android.content.Context
import android.content.SharedPreferences
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** SpPlugin */
class SpPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var sp:SharedPreferences

  override fun onAttachedToEngine( flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    sp = flutterPluginBinding.applicationContext.getSharedPreferences(NAME,Context.MODE_PRIVATE)
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "sp")
    channel.setMethodCallHandler(this)

  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    private const val NAME:String ="flutter_sp"
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "sp")
      channel.setMethodCallHandler(SpPlugin())
    }
  }

  override fun onMethodCall( call: MethodCall,  result: Result) {
    when(call.method){
      "getConfig"->{
        val key = call.argument<String>("key")
        val defaultValue = call.argument<Any>("defValue")
        if (key.isNullOrEmpty()){
          result.notImplemented()
        }else{
          when(defaultValue){
            is String -> sp.getString(key,defaultValue)
            is Boolean ->sp.getBoolean(key,defaultValue)
            is Int ->sp.getInt(key,defaultValue)
            is Long->sp.getLong(key,defaultValue)
            is Float->sp.getFloat(key,defaultValue)
          }

        }
      }
      "saveConfig"->{
        val key = call.argument<String>("key")
        val value = call.argument<Any>("value")
        if (key.isNullOrEmpty()){
          result.notImplemented()
        }else{
          sp.edit().apply {
            when(value){
              is String -> putString(key,value)
              is Boolean ->putBoolean(key,value)
              is Int ->putInt(key,value)
              is Long->putLong(key,value)
              is Float->putFloat(key,value)
            }
          }.commit()
        }
      }
      "delConfig"->{
        sp.edit().clear().commit()
      }

    }

  }

  override fun onDetachedFromEngine( binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}

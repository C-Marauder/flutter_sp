import 'dart:async';

import 'package:flutter/services.dart';

class Sp {
  static const MethodChannel _channel =
      const MethodChannel('sp');


  static Future<T> getValue<T>(String key,T defaultValue) async{
    T value = await _channel.invokeMethod('getConfig',[key,defaultValue]);
    return value;
  }
  static saveValue<T> (String key,T value) async{
    await _channel.invokeMethod('saveConfig',[key,value]);
  }
}

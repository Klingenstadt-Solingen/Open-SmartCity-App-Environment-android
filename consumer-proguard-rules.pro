# Add project specific ProGuard rules here.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Fix obfuscation removing classes referenced in fields of serialized classes


-keep @com.parse.ParseClassName public class *

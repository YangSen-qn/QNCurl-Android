#LOCAL_PATH := $(call my-dir)
#
#include $(CLEAR_VARS)
#LOCAL_LDLIBS := -llog
#
#LOCAL_MODULE    := qn-curl

#C_SOURCE_LIST := $(wildcard $(LOCAL_PATH)/*.c)
#C_SOURCE_LIST := $(wildcard $(LOCAL_PATH)/code/*.so)
#LOCAL_SRC_FILES := $(MY_CPP_LIST:$(LOCAL_PATH)/%=%)

#LOCAL_SRC_FILES := $(LOCAL_PATH)/native/curl_native_test.cpp
#
#include $(BUILD_SHARED_LIBRARY)

LOCAL_PATH := $(call my-dir)
################################################################################
#crypto prebuilt
#include $(CLEAR_VARS)
#LOCAL_MODULE := crypto-built
#LOCAL_SRC_FILES := \
#  $(LOCAL_PATH)/lib/$(TARGET_ARCH_ABI)/libcrypto.a
#include $(PREBUILT_STATIC_LIBRARY)
################################################################################
#ssl prebuilt
#include $(CLEAR_VARS)
#LOCAL_MODULE := ssl-built
#LOCAL_SRC_FILES := \
#  $(LOCAL_PATH)/lib/$(TARGET_ARCH_ABI)/libssl.a
#include $(PREBUILT_STATIC_LIBRARY)
################################################################################
#quiche prebuilt
include $(CLEAR_VARS)
LOCAL_MODULE := quiche-built
LOCAL_SRC_FILES := \
  $(LOCAL_PATH)/lib/$(TARGET_ARCH_ABI)/libquiche.a
#LOCAL_STATIC_LIBRARIES := ssl-built crypto-built
  #LOCAL_WHOLE_STATIC_LIBRARIES += ssl-built crypto-built
include $(PREBUILT_STATIC_LIBRARY)
################################################################################

#nghttp2 prebuilt
include $(CLEAR_VARS)
LOCAL_MODULE := nghttp2-built
LOCAL_SRC_FILES := \
  $(LOCAL_PATH)/lib/$(TARGET_ARCH_ABI)/libnghttp2.a
# LOCAL_STATIC_LIBRARIES := ssl-built quiche-built
# LOCAL_WHOLE_STATIC_LIBRARIES += ssl-built quiche-built
include $(PREBUILT_STATIC_LIBRARY)
################################################################################
#cURL prebuilt
include $(CLEAR_VARS)
LOCAL_MODULE := curl-built
LOCAL_SRC_FILES := \
  $(LOCAL_PATH)/lib/$(TARGET_ARCH_ABI)/libcurl.a
LOCAL_STATIC_LIBRARIES := nghttp2-built quiche-built
#LOCAL_WHOLE_STATIC_LIBRARIES += nghttp2-built quiche-built crypto-built ssl-built
include $(PREBUILT_STATIC_LIBRARY)
################################################################################
#qn-curl
include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog
LOCAL_MODULE := qn-curl

MY_CPP_LIST := $(wildcard $(LOCAL_PATH)/native/*.cpp)
LOCAL_SRC_FILES := $(MY_CPP_LIST:$(LOCAL_PATH)/%=%)
#	$(LOCAL_PATH)/native/curl_native.cpp \
#	$(LOCAL_PATH)/native/curl_context.cpp \
#	$(LOCAL_PATH)/native/curl_transaction_metrics.cpp \
#	$(LOCAL_PATH)/native/curl_configuration.cpp

LOCAL_STATIC_LIBRARIES := curl-built
COMMON_CFLAGS := -Werror -DANDROID

ifeq ($(TARGET_ARCH),arm)
  LOCAL_CFLAGS := -mfpu=vfp -mfloat-abi=softfp -fno-short-enums
endif

LOCAL_CFLAGS += $(COMMON_CFLAGS)
#LOCAL_LDLIBS := -lz -llog -Wl,-s
LOCAL_LDLIBS := -lz -llog -Wl
LOCAL_CPPFLAGS += -std=gnu++0x
LOCAL_C_INCLUDES += \
  $(NDK_PATH)/platforms/$(TARGET_PLATFORM)/arch-$(TARGET_ARCH)/usr/include \
  $(LOCAL_PATH)/lib/include

include $(BUILD_SHARED_LIBRARY)
################################################################################
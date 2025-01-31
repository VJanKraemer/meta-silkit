SUMMARY = "Vector SIL Kit – Open-Source Library for Connecting Software-in-the-Loop Environments."
HOMEPAGE = "https://github.com/vectorgrp/sil-kit"
BUGTRACKER = "https://github.com/vectorgrp/sil-kit/issues"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=95654a2cab6505c130d8de2d06a4d1b8"

SRC_URI += " \
    gitsm://github.com/vectorgrp/sil-kit;protocol=https;nobranch=1 \
    file://run-ptest \
    file://silkit-registry.service \
"
SRCREV ?= "9bd79455d47ad398581c862e1cc5ad4d472a3c55"
S = "${WORKDIR}/git"

inherit cmake ptest systemd

EXTRA_OECMAKE:append = " -DSILKIT_BUILD_DEMOS=OFF"
EXTRA_OECMAKE:append = " -DSILKIT_INSTALL_SOURCE=OFF"
EXTRA_OECMAKE:append = " -DSILKIT_BUILD_LINUX_PACKAGE=ON"

SYSTEMD_SERVICE:silkit-utils = "silkit-registry.service"
SYSTEMD_AUTO_ENABLE = "disable"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -Dm 0644 ${WORKDIR}/silkit-registry.service \
                 ${D}${systemd_system_unitdir}/silkit-registry.service

}

do_install_ptest() {
    cp ${B}/Release/SilKitUnitTests ${D}${PTEST_PATH}
    cp ${B}/Release/*.yaml ${D}${PTEST_PATH}
    cp ${B}/Release/*.json ${D}${PTEST_PATH}
    cp ${B}/Release/libWrong*.so ${D}${PTEST_PATH}

    cp -rf ${B}/Release/ConfigSnippets ${D}${PTEST_PATH}
    cp -rf ${B}/Release/silkit_library_test ${D}${PTEST_PATH}
}

PACKAGES += "silkit-utils"

FILES:${PN} = "${libdir}/lib*.so*"

FILES:${PN}-dev += "${libdir}/cmake/*"
FILES:${PN}-dev += "${includedir}/*"

FILES:silkit-utils += "${bindir}/sil-kit-monitor"
FILES:silkit-utils += "${bindir}/sil-kit-registry"
FILES:silkit-utils += "${bindir}/sil-kit-system-controller"
FILES:silkit-utils += "${mandir}/man1/*.1.gz"
FILES:silkit-utils += "${systemd_system_unitdir}/*.service"

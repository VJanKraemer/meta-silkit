SUMMARY = "Vector SIL Kit Adapter for TAP devices - A collection of software to connect TAP devices to the Vector SIL Kit"
HOMEPAGE = "https://github.com/vectorgrp/sil-kit-adapters-tap"
BUGTRACKER = "https://github.com/vectorgrp/sil-kit-adapters-tap/issues"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8975cdf922b02fa69409d1deb2947d5f"

DEPENDS:append = " libsilkit"

SRC_URI += "gitsm://github.com/vectorgrp/sil-kit-adapters-tap;protocol=https;nobranch=1"
SRCREV ?= "v1.0.2"
S = "${WORKDIR}/git"

inherit pkgconfig cmake

RDEPENDS:${PN} = " \
    libsilkit \
"

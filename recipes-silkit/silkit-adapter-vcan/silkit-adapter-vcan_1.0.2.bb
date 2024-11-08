DESCRIPTION = "silkit adapter to adapt CAN-Frames"
HOMEPAGE = "https://github.com/vectorgrp/sil-kit-adapters-vcan"
BUGTRACKER = "https://github.com/vectorgrp/sil-kit-adapters-vcan/issues"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8975cdf922b02fa69409d1deb2947d5f"

DEPENDS:append = " libsilkit"
RDEPENDS:${PN} = " \
    libsilkit \
"

S = "${WORKDIR}/git"

inherit pkgconfig cmake

SRC_URI += "gitsm://github.com/vectorgrp/sil-kit-adapters-vcan;protocol=https;nobranch=1"
SRCREV ?= "v1.0.2"
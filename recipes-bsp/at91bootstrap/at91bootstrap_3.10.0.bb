require at91bootstrap.inc

LIC_FILES_CHKSUM = "file://main.c;endline=27;md5=a2a70db58191379e2550cbed95449fbd"

COMPATIBLE_MACHINE = '(sama7g5ek-sd)'

SRC_URI = "git://github.com/linux4sam/at91bootstrap.git;protocol=https"

PV = "3.10.0+git${SRCPV}"
SRCREV = "72be09e758d4cd1adf5734c868fef87c7dee4fa4"

S = "${WORKDIR}/git"
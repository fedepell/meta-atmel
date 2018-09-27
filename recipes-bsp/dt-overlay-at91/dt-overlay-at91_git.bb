DESCRIPTION = "Compile AT91 board device tree overlays and pack them in a FIT image"
LICENSE = "(GPLv2+ & MIT)"
LIC_FILES_CHKSUM = "file://COPYING;md5=775626b7bc958bdcc525161f725ece0f \
		    file://LICENSES/GPL-2.0;md5=e6a75371ba4d16749254a51215d13f97 \
		    file://LICENSES/MIT;md5=e8f57dd048e186199433be2c41bd3d6d"

inherit deploy

COMPATIBLE_MACHINE = '(sama5d3-xplained|sama5d3-xplained-sd|at91sam9x5ek|at91sam9x5ek-sd|sama5d4-xplained|sama5d4-xplained-sd|sama5d2-xplained|sama5d2-xplained-sd|sama5d2-xplained-emmc|sama5d2-ptc-ek|sama5d2-ptc-ek-sd|sama5d27-som1-ek|sama5d27-som1-ek-sd)'

SRC_URI = "git://github.com/linux4sam/dt-overlay-at91.git;protocol=https"

PV = "1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

DEPENDS = "virtual/kernel u-boot-mkimage-native dtc-native"

S = "${WORKDIR}/git"

do_compile[depends] += "virtual/kernel:do_deploy"

do_compile () {
	if [ -e ${AT91BOOTSTRAP_MACHINE}/${AT91BOOTSTRAP_MACHINE}*.dtso ]; then
		echo "Compiling DTBOs"
		oe_runmake DTC=dtc KERNEL_DIR=${STAGING_KERNEL_DIR} KERNEL_BUILD_DIR=${KERNEL_PATH} ${AT91BOOTSTRAP_MACHINE}_dtbos
	else
		echo "No DTBOs to compile"
	fi

	# Over-ride itb target in Makefile
	if [ -e ${AT91BOOTSTRAP_MACHINE}.its ]; then
		echo "Creating the FIT image"
		DTC_OPTIONS="-Wno-unit_address_vs_reg -Wno-graph_child_address -Wno-pwms_property"
		mkimage -D "-i${DEPLOY_DIR_IMAGE} -p 1000 ${DTC_OPTIONS}" -f ${AT91BOOTSTRAP_MACHINE}.its ${AT91BOOTSTRAP_MACHINE}.itb
	else
		echo "No its file to create FIT images"
	fi
}

FILES_${PN} = "/boot/*.*"

addtask install after do_compile

do_install () {
	# Copy files to /boot
	if [ -e ${AT91BOOTSTRAP_MACHINE}/${AT91BOOTSTRAP_MACHINE}*.dtbo ]; then
		install -d ${D}/boot
		install ${AT91BOOTSTRAP_MACHINE}/${AT91BOOTSTRAP_MACHINE}*.dtbo ${D}/boot
	fi;

	if [ -e ${AT91BOOTSTRAP_MACHINE}.itb ]; then
		install -d ${D}/boot
		fit_image_basename="fitImage-${MACHINE}"
		install ${AT91BOOTSTRAP_MACHINE}.itb ${D}/boot/${fit_image_basename}.itb
		install ${AT91BOOTSTRAP_MACHINE}.its ${D}/boot/${fit_image_basename}.its
	fi;
}

addtask deploy after do_install

do_deploy () {
	fit_image_basename="fitImage-${MACHINE}"

	#echo "Copying ${fit_image_basename}.itb and source file to ${DEPLOYDIR}..."
	install ${AT91BOOTSTRAP_MACHINE}.itb ${DEPLOYDIR}/${fit_image_basename}.itb
	install ${AT91BOOTSTRAP_MACHINE}.its ${DEPLOYDIR}/${fit_image_basename}.its

	cd ${DEPLOYDIR}
        ln -sf ${fit_image_basename}.itb ${MACHINE}.itb
        ln -sf ${fit_image_basename}.its ${MACHINE}.its
}

DESCRIPTION = "HID image based on SAM9x60"

LICENSE = "MIT"

inherit core-image

EXTRA_IMAGE_FEATURES = " debug-tweaks ssh-server-openssh package-management "


IMAGE_INSTALL_append = " \
	binutils \
	gdbserver \
	strace \
	iproute2 \
	canutils \
	mtd-utils \
	mtd-utils-ubifs \
	devmem2 \
	mchp-wireless-firmware \
	i2c-tools \
	minicom \
	ethtool \
	dosfstools \
	e2fsprogs \
	usbutils \
	iw \
	wpa-supplicant \
	opkg \
	opkg-collateral \
	json-c \
	ppp \
	ppp-tools \
	lua \
	luaposix \
	luacjson \
	luasys \
	luasocket \
	luasql \
	luasnmp \
	lualfs \
	luaexpat \
	curl \
	libmodbus \
	openvpn \
	lighttpd \
	lighttpd-module-openssl \
	lighttpd-module-rewrite \
	lighttpd-module-redirect \
	lighttpd-module-alias \
	lighttpd-module-auth \
	lighttpd-module-authn-file \
	lighttpd-module-evasive \
	lighttpd-module-usertrack \
	lighttpd-module-setenv \
	lighttpd-module-cgi \
	lighttpd-module-compress \
	libmicrohttpd \
	ntpdate \
	procps \
	xz \
	wget \
	tar \
	sysstat \
	screen \
	msmtp \
	lsof \
	iptables \
	gzip \
	grep \
	bzip2 \
	bash \
	dos2unix \
	nano \
	traceroute \
	hostapd \
	iproute2-ss \
	iproute2-devlink \
	iproute2-genl \
	iproute2-ifstat \
	iproute2-lnstat \
	iproute2-nstat \
	iproute2-rtacct \
	iproute2-tc \
	iproute2-tipc \
	libxml2-utils \
	sqlite3 \
	util-linux \
	avahi-utils \
	usb-modeswitch \
	htop \
	dhcp-client \
	bind-utils \
	netcat \
	iputils \
	coreutils \
	gd \
	fswebcam \
	kernel-module-pwrseq-wilc \
	kernel-module-wilc-sdio \
	kernel-module-cfg80211 \
"


# Add wpa supplicant static part to toolchain
TOOLCHAIN_TARGET_TASK += " wpa-supplicant-staticdev"

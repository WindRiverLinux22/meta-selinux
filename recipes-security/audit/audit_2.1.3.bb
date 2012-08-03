SUMMARY = "User space tools for kernel auditing"
DESCRIPTION = "The audit package contains the user space utilities for \
storing and searching the audit records generated by the audit subsystem \
in the Linux kernel."
HOMEPAGE = "http://people.redhat.com/sgrubb/audit/"
SECTION = "base"
PR = "r1"
LICENSE = "GPLv2+ & LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = "http://people.redhat.com/sgrubb/audit/audit-2.1.3.tar.gz \
	   file://disable-ldap.patch \
	   file://audit-python.patch"

SRC_URI += "file://audit-for-cross-compiling.patch"

inherit autotools pythonnative

SRC_URI[md5sum] = "abf26e3ac09f666905c5636dd24611fa"
SRC_URI[sha256sum] = "1c61858d8ed299128aa6bd8e85bac758bfe33e61358d259e52acb7d961fee90e"

DEPENDS += "python tcp-wrappers libcap-ng linux-libc-headers (>= 2.6.30)"

EXTRA_OECONF += "--without-prelude --with-libwrap --enable-gssapi-krb5=no --disable-ldap --with-libcap-ng=yes"

EXTRA_OEMAKE += "PYLIBVER='python${PYTHON_BASEVERSION}' PYINC='${STAGING_INCDIR}/$(PYLIBVER)'"

SUMMARY_audispd-plugins = "Plugins for the audit event dispatcher"
DESCRIPTION_audispd-plugins = "The audispd-plugins package provides plugins for the real-time \
interface to the audit system, audispd. These plugins can do things \
like relay events to remote machines or analyze events for suspicious \
behavior."

PACKAGES =+ "audispd-plugins ${PN}-libs"
PACKAGES += "${PN}-python"

FILES_${PN}-libs += "${sysconfdir}/libaudit.conf ${libdir}/libaudit.so.1* ${libdir}/libauparse.so.*"
FILES_${PN} += "${bindir} ${sbindir}"
FILES_audispd-plugins += "${sysconfdir}/audisp ${sbindir}/audisp*"
FILES_${PN}-dbg += "${libdir}/python${PYTHON_BASEVERSION}/*/.debug"
FILES_${PN}-python = "${libdir}/python${PYTHON_BASEVERSION}"


# The executables in lib/, which are named as gen_*_h, will run on the hosts to create
# *_tables.h/*tabs.h header files for the targets.
# In some old hosts, build will fail because audit.h in the old linux-libc-headers (<= 2.6.29) 
# has a incomplete netlink message list for the audit system.
do_compile_prepend() {
        mkdir -p ${S}/lib/linux
        cp -f ${STAGING_INCDIR}/linux/audit.h ${S}/lib/linux/
        cp -f ${STAGING_INCDIR}/linux/elf-em.h ${S}/lib/linux/
}

do_install_append() {
	rm -f ${D}/${libdir}/python${PYTHON_BASEVERSION}/site-packages/*.a
	rm -f ${D}/${libdir}/python${PYTHON_BASEVERSION}/site-packages/*.la
}

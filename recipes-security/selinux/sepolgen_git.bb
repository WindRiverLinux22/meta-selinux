SUMMARY = "Python modules for supporting various SELinux utilities."
DESCRIPTION = "\
This package contains a Python module that forms the core of the \
modern audit2allow (which is a part of the package policycoreutils). \
The sepolgen library is structured to give flexibility to the \
application using it. The library contains: Reference Policy \
Representation, which are Objects for representing policies and the \
reference policy interfaces. Secondly, it has objects and algorithms \
for representing access and sets of access in an abstract way and \
searching that access. It also has a parser for reference policy \
"headers". It contains infrastructure for parsing SELinux related \
messages as produced by the audit system. It has facilities for \
generating policy based on required access."
 
SECTION = "base"
PR = "r1"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
DEFAULT_PREFERENCE = "-1"

include selinux_git.inc

SRCREV = "339f8079d7b9dd1e0b0138e2d096dc7c60b2092e"
PV = "1.1.5+git${SRCPV}" 
 
FILES_${PN} = "${libdir}/python${PYTHON_BASEVERSION}/site-packages/*"

DEPENDS += "python"

FILES_${PN} += "${libdir}/python${PYTHON_BASEVERSION}/site-packages \
		/var/lib/sepolgen"

do_install() {
	oe_runmake DESTDIR=${D} \
			PYTHONLIBDIR='${libdir}/python${PYTHON_BASEVERSION}/site-packages' \
			install
}

BBCLASSEXTEND = "native"

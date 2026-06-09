SUMMARY = "llama.cpp inference engine"
HOMEPAGE = "https://github.com/ggml-org/llama.cpp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=223b26b3c1143120c87e2b13111d3e99"

SRC_URI = "gitsm://github.com/ggml-org/llama.cpp.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit cmake

DEPENDS += "openssl"

EXTRA_OECMAKE += "\
    -DGGML_OPENMP=OFF \
    -DGGML_NATIVE=OFF \
    -DBUILD_SHARED_LIBS=OFF \
    -DCMAKE_BUILD_TYPE=Release \
    -DLLAMA_BUILD_TESTS=OFF \
    -DOPENSSL_ROOT_DIR=${STAGING_DIR_TARGET}/usr \
"

do_install:append() {
    install -d ${D}${bindir}
    for bin in llama-cli llama-server llama-bench llama-run llama-tokenize llama-quantize; do
        if [ -f ${B}/bin/$bin ]; then
            install -m 0755 ${B}/bin/$bin ${D}${bindir}/
        fi
    done
}

FILES:${PN} += "${bindir}/*"

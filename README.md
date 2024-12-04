# meta-silkit - Yocto Recipes for SIL Kit + Adapters
This repository contains a meta layer for the Yocto Project. It allows you to build SIL Kit as part of your custom distro build with bitbake and the Yocto Project.
It contains recipes for the following packages:

* libsilkit
    * The SIL Kit library, developer and utility tools
* silkit-adapter-tap
    * Vector SIL Kit Adapter for TAP devices
* silkit-adapter-vcan
    * Vector SIL Kit Adapter for virtual CAN (SocketCAN) devices

For more information, please visit:

* https://github.com/vectorgrp/sil-kit
* https://github.com/vectorgrp/sil-kit-adapters-tap
* https://github.com/vectorgrp/sil-kit-adapters-vcan

## How to build
This is a step-by-step example, how-to build a poky `core-image-minimal` containing libsilkit for a QEMU x86-64 image.

### Setup
> [!WARNING]
> The build directory for a minimal image (poky core-image-minimal) is around 70GB. To be safe, it is better to have at least 100GB free on your harddrive for the build directory!

First, we need to install all prerequisite dependencies
```
$ > sudo apt install gawk wget git diffstat unzip texinfo gcc build-essential chrpath socat cpio python3 python3-pip python3-pexpect xz-utils debianutils iputils-ping python3-git python3-jinja2 python3-subunit zstd liblz4-tool file locales libacl1
$ > sudo locale-gen en_US.UTF-8
```

We now need to clone the poky distribution repository. We currently only support Poky 5.0.4 (scarthgap), so we can clone the scarthgap branch directly!
```
$ > git clone --depth 1 -b scarthgap https://git.yoctoproject.org/poky
```

To setup the build environment for the Linux image, poky comes with a handy setup script, which we can source:
```
$ > cd poky
$ > source oe-init-build-env
```

This will create the appropriate environment and drop us directly into the `build` directory. We can now configure our build to create the exact Linux distro we desire.

### Build Configuration
But first we need to tell Yocto about our meta-silkit layer. We do this in the `conf/bblayers.conf`
```
# conf/bblayers.conf
.
.
BBLAYERS ?= " \
  /Path/to/poky/meta \
  /Path/to/poky/meta-poky \
  /Path/to/poky/meta-yocto-bsp \
  /Path/to/meta-silkit \
  "
```

Here we added the path to our `meta-silkit` layer at the end of the `BBLAYERS` variable.

We can now configure our build through the `conf/local.conf` file
```
# conf/bblayers.conf
.
.
MACHINE ??= "qemux86-64"
.
.
IMAGE_INSTALL:append = "libsilkit libsilkit-dev silkit-utils silkit-adapter-tap silkit-adapter-vcan"
```

The `MACHINE` variable selects the desired CPU architecture for which to build. In our case we go with a QEMU image for the x86-64 architecture. Other `MACHINE` values you can select out of the box include `qemuarm` and `qemuarm64`. If you develop for a specific hardware target, you should set the `MACHINE` to the
appropriate values for the Board Support Layer of your target.

Then we must tell Yocto to include the packages build by our layer to the image via the `IMAGE_INSTALL` variable. Since we only want to add our packages to the default list of packages, we append them via the `:append` syntax.

### Building the package
We build the image via
```
$ > bitbake core-image-minimal
```

This is the most minimalistic console image Yocto provides out of the box. If you want a more full-fledged console image, you can use `core-image-full-cmdline` or `core-image-weston` for an image with a minimal Wayland GUI.

> [!WARNING]
> The build process can take a long time even with a powerful workstation.

### Running the image
```
$ > runqemu
```

This will automatically start the last build image in a QEMU instance. You should now be able to start the sil-kit-registry
```
root@qemux86-64: ~# sil-kit-registry
Vector SIL Kit -- Registry, SIL Kit version 4.0.54

SIL Kit Registry listening on silkit://localhost:8500
Press Ctrl-C to terminate...
_
```

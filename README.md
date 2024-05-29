# LegacyCompatibilityUtils

(CompatLib in short) Made most 1.7.2 mods working on 1.7.10

This is done by re-add removed classes and srg methods/fields.

Exceptions:
1. Coremods with `@MCVersion("1.7.2")`.
2. 1.7.2 Mods that uses obfuscated ("Notch") codes like reflection helper or ASM.
3. The Mod was using 1.7.2's player skin related codes.
4. See [diff.yml](https://github.com/HowardZHY/LegacyCompatibilityUtils-1.7.10/blob/main/diff.yml) for other changes.

Requires [UniMixins](https://github.com/LegacyModdingMC/UniMixins).

This mod contains following libs:

A Relocated https://github.com/FabricCompatibilityLayers/CursedMixinExtensions

1.10.5 version of https://github.com/JOML-CI/JOML

# Notice

If anyone is interested at making another runtime deobf remapper or knows how to fix SpongeVanilla's on 1.7.10, feel free to open a topic in Github Discussion.

Submit an issue if you met incompatibilities.

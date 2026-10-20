/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */


package app.morphe.extension.instagram.entity;

import app.morphe.extension.crimera.legacydownloader.MediaType;

public class VideoData extends Entity implements MediaInterface {
    public VideoData(Object obj) {
        super(obj);
    }

    public Integer getHeight() throws Exception {
        Integer height = nativeHeight(this.obj);
        return height != null ? height : 0;
    }

    public Integer getWidth() throws Exception {
        Integer width = nativeWidth(this.obj);
        return width != null ? width : 0;
    }

    private Integer getCodec() throws Exception {
        Integer codec = nativeCodec(this.obj);
        return codec != null ? codec : 0;
    }

    public String getVariantTag() {
        try{
            return this.getHeight()+"x"+this.getWidth()+"-"+this.getCodec();
        } catch (Exception e) {
            return "unknown";
        }
    }

    public String getUrl() throws Exception {
        return nativeUrl(this.obj);
    }

    public MediaType getMediaType(){
        return MediaType.VIDEO;
    }

    // Replaced with direct calls resolved from the original APK while patching.
    private static Integer nativeHeight(Object video) { throw new IllegalStateException("Unbound video height"); }
    private static Integer nativeWidth(Object video) { throw new IllegalStateException("Unbound video width"); }
    private static Integer nativeCodec(Object video) { throw new IllegalStateException("Unbound video codec"); }
    private static String nativeUrl(Object video) { throw new IllegalStateException("Unbound video URL"); }

}

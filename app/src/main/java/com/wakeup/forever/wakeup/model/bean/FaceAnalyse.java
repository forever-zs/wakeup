package com.wakeup.forever.wakeup.model.bean;

import java.util.List;

/**
 * Created by forever on 2016/12/3.
 */

public class FaceAnalyse {

    private String image_id;
    private String request_id;
    private int time_used;

    private List<FacesBean> faces;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public List<FacesBean> getFaces() {
        return faces;
    }

    public void setFaces(List<FacesBean> faces) {
        this.faces = faces;
    }

    public static class FacesBean {

        private AttributesBean attributes;

        private FaceRectangleBean face_rectangle;
        private String face_token;


        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public FaceRectangleBean getFace_rectangle() {
            return face_rectangle;
        }

        public void setFace_rectangle(FaceRectangleBean face_rectangle) {
            this.face_rectangle = face_rectangle;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public static class AttributesBean {
            /**
             * value : Female
             */

            private GenderBean gender;
            /**
             * value : 21
             */

            private AgeBean age;
            /**
             * value : None
             */

            private GlassBean glass;
            /**
             * yaw_angle : -26.625063
             * pitch_angle : 12.921974
             * roll_angle : 22.814377
             */

            private HeadposeBean headpose;
            /**
             * threshold : 30.1
             * value : 2.566890001296997
             */

            private SmileBean smile;

            public GenderBean getGender() {
                return gender;
            }

            public void setGender(GenderBean gender) {
                this.gender = gender;
            }

            public AgeBean getAge() {
                return age;
            }

            public void setAge(AgeBean age) {
                this.age = age;
            }

            public GlassBean getGlass() {
                return glass;
            }

            public void setGlass(GlassBean glass) {
                this.glass = glass;
            }

            public HeadposeBean getHeadpose() {
                return headpose;
            }

            public void setHeadpose(HeadposeBean headpose) {
                this.headpose = headpose;
            }

            public SmileBean getSmile() {
                return smile;
            }

            public void setSmile(SmileBean smile) {
                this.smile = smile;
            }

            public static class GenderBean {
                private String value;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class AgeBean {
                private int value;

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class GlassBean {
                private String value;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class HeadposeBean {
                private double yaw_angle;
                private double pitch_angle;
                private double roll_angle;

                public double getYaw_angle() {
                    return yaw_angle;
                }

                public void setYaw_angle(double yaw_angle) {
                    this.yaw_angle = yaw_angle;
                }

                public double getPitch_angle() {
                    return pitch_angle;
                }

                public void setPitch_angle(double pitch_angle) {
                    this.pitch_angle = pitch_angle;
                }

                public double getRoll_angle() {
                    return roll_angle;
                }

                public void setRoll_angle(double roll_angle) {
                    this.roll_angle = roll_angle;
                }
            }

            public static class SmileBean {
                private double threshold;
                private double value;

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }
            }
        }

        public static class FaceRectangleBean {
            private int width;
            private int top;
            private int left;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}

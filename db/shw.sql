/*
 Navicat Premium Data Transfer

 Source Server         : mongodb@localhost
 Source Server Type    : MongoDB
 Source Server Version : 40203
 Source Host           : localhost:27017
 Source Schema         : shw

 Target Server Type    : MongoDB
 Target Server Version : 40203
 File Encoding         : 65001

 Date: 03/08/2020 13:58:50
*/


// ----------------------------
// Collection structure for group
// ----------------------------
db.getCollection("group").drop();
db.createCollection("group");

// ----------------------------
// Documents of group
// ----------------------------
db.getCollection("group").insert([ {
    _id: "c3ac4579-7b48-40bf-9d31-f9a9122675c4",
    "group_name": "11",
    "teacher_name": "setTeacherName",
    "teacher_number": "setTeacherNumber",
    code: "setCode",
    "gmt_create": ISODate("2020-07-08T08:45:54.031Z"),
    "gmt_modified": ISODate("2020-07-08T08:45:54.031Z"),
    _class: "top.itning.server.shwgroup.entity.Group"
} ]);
db.getCollection("group").insert([ {
    _id: "62eb6d9e59e246149df517abbf417e10",
    "group_name": "测试创建群组1",
    "teacher_name": "测试教师名1",
    "teacher_number": "0002",
    code: "62eb6d9e59e246149df517abbf417e10",
    "gmt_create": ISODate("2020-07-08T08:50:14.795Z"),
    "gmt_modified": ISODate("2020-07-08T08:50:14.795Z"),
    _class: "top.itning.server.shwgroup.entity.Group"
} ]);

// ----------------------------
// Collection structure for student
// ----------------------------
db.getCollection("student").drop();
db.createCollection("student");

// ----------------------------
// Documents of student
// ----------------------------
db.getCollection("student").insert([ {
    _id: "1",
    "login_name": "test",
    "login_pwd": "123456",
    name: "张志杰",
    "clazz_id": "1",
    _class: "top.itning.server.shwsecurity.entity.Student"
} ]);
db.getCollection("student").insert([ {
    _id: ObjectId("5f27882eb47dc36503641549"),
    "login_name": "huangguoyong",
    "login_pwd": "123456",
    name: "黄国勇",
    "clazz_id": "2",
    _class: "top.itning.server.shwsecurity.entity.Student"
} ]);
db.getCollection("student").insert([ {
    _id: ObjectId("5f278ab23d6f744604f43019"),
    "login_name": "test2",
    "login_pwd": "123456",
    name: "test2",
    "clazz_id": "1",
    _class: "top.itning.server.shwsecurity.entity.Student"
} ]);

// ----------------------------
// Collection structure for student_group
// ----------------------------
db.getCollection("student_group").drop();
db.createCollection("student_group");

// ----------------------------
// Documents of student_group
// ----------------------------
db.getCollection("student_group").insert([ {
    _id: "af34b58f-bbf0-47fd-885c-4ecd10e673bd|8610d2e0-364d-4d00-9b0e-5958c7e16a99",
    "student_number": "af34b58f-bbf0-47fd-885c-4ecd10e673bd",
    "group_id": "8610d2e0-364d-4d00-9b0e-5958c7e16a99",
    "gmt_create": ISODate("2020-07-08T09:04:16.431Z"),
    "gmt_modified": ISODate("2020-07-08T09:04:16.431Z"),
    _class: "top.itning.server.shwstudentgroup.entity.StudentGroup"
} ]);

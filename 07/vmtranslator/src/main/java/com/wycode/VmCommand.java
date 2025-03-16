package com.wycode;

public enum VmCommand {
    INIT(
            "// bootstrap\n" +
                    "@256            \n" +
                    "D=A\n" +
                    "@SP\n" +
                    "M=D            \n"
    ),
    RETURN(
            "//RETURN\n" +
                    "@LCL\n" +
                    "D=M\n" +
                    "@frame\n" +
                    "M=D // FRAME = LCL\n" +
                    "@5\n" +
                    "D=D-A\n" +
                    "A=D\n" +
                    "D=M\n" +
                    "@return_address\n" +
                    "M=D // RET = *(FRAME-5)\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "@ARG\n" +
                    "A=M\n" +
                    "M=D // *ARG = pop()\n" +
                    "@ARG\n" +
                    "D=M+1\n" +
                    "@SP\n" +
                    "M=D // SP = ARG+1\n" +
                    "@frame\n" +
                    "D=M-1\n" +
                    "A=D\n" +
                    "D=M\n" +
                    "@THAT\n" +
                    "M=D // THAT = *(FRAME-1)\n" +
                    "@2\n" +
                    "D=A\n" +
                    "@frame\n" +
                    "D=M-D\n" +
                    "A=D\n" +
                    "D=M\n" +
                    "@THIS\n" +
                    "M=D // THIS = *(FRAME-2)\n" +
                    "@3\n" +
                    "D=A\n" +
                    "@frame\n" +
                    "D=M-D\n" +
                    "A=D\n" +
                    "D=M\n" +
                    "@ARG\n" +
                    "M=D // ARG = *(FRAME-3)\n" +
                    "@4\n" +
                    "D=A\n" +
                    "@frame\n" +
                    "D=M-D\n" +
                    "A=D\n" +
                    "D=M\n" +
                    "@LCL\n" +
                    "M=D // LCL = *(FRAME-4)\n" +
                    "@return_address\n" +
                    "A=M\n" +
                    "0;JMP // goto RET\n"

    ),
    CALL(
            "// CALL {SEGMENT} {INDEX}\n" +
                    "@{SEGMENT}$ret.{ID}\n" +
                    "D=A\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n" +
                    "// push LCL\n" +
                    "@LCL\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n" +
                    "// push ARG\n" +
                    "@ARG\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n" +
                    "// push THIS\n" +
                    "@THIS\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n" +
                    "// push THAT\n" +
                    "@THAT\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n" +
                    "// ARG = SP-n-5\n" +
                    "@SP\n" +
                    "D=M\n" +
                    "@{INDEX}\n" +
                    "D=D-A\n" +
                    "@5\n" +
                    "D=D-A\n" +
                    "@ARG\n" +
                    "M=D\n" +
                    "// LCL = SP\n" +
                    "@SP\n" +
                    "D=M\n" +
                    "@LCL\n" +
                    "M=D\n" +
                    "// goto f\n" +
                    "@{SEGMENT}\n" +
                    "0;JMP\n" +
                    "// (return-address)\n" +
                    "({SEGMENT}$ret.{ID})"
    ),
    IF_GOTO(
            "// IF-GOTO {SEGMENT}\n" +
                    "@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@{SEGMENT}\n" +
                    "D;JNE\n"
    ),
    GOTO(
            "// GOTO {SEGMENT}\n" +
                    "@{SEGMENT}\n" +
                    "0;JMP\n"
    ),
    LABEL(
            "// LABEL {SEGMENT}\n" +
                    "({SEGMENT})\n"
    ),
    PUSH_CONSTANT(
            "// PUSH CONSTANT {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=A\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
    ),
    PUSH_POINTER(
            "// PUSH POINT {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
    ),
    PUSH_TEMP(
            "// PUSH TEMP {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=A\n" +
                    "@5\n" +
                    "A=D+A\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
    ),
    PUSH_STATIC(
            "// PUSH STATIC {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=A\n" +
                    "@16\n" +
                    "A=D+A\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
    ),
    PUSH(
            "// PUSH {SEGMENT} {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=A\n" +
                    "@{SEGMENT}\n" +
                    "AD=D+M\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n"
    ),
    POP_STATIC(
            "//POP TEMP {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=A\n" +
                    "@16\n" +
                    "D=D+A\n" +
                    "@R13\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@R13\n" +
                    "A=M\n" +
                    "M=D\n"
    ),
    POP_POINTER(
            "// POP POINT {INDEX}\n" +
                    "@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@{INDEX}\n" +
                    "M=D\n"
    ),
    POP_TEMP(
            "//POP TEMP {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=A\n" +
                    "@5\n" +
                    "D=D+A\n" +
                    "@R13\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@R13\n" +
                    "A=M\n" +
                    "M=D\n"
    ),
    POP(
            "//POP {SEGMENT} {INDEX}\n" +
                    "@{INDEX}\n" +
                    "D=A\n" +
                    "@{SEGMENT}\n" +
                    "D=D+M\n" +
                    "@R13\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@R13\n" +
                    "A=M\n" +
                    "M=D\n"
    ),
    ADD(
            "// add\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "M=D+M\n"
    ),
    SUB(
            "//SUB\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "M=M-D\n"
    ),
    NEG("//NEG\n" +
            "@SP\n" +
            "A=M-1\n" +
            "M=-M\n"),
    EQ("// EQ\n" +
            "@SP\n" +
            "M=M-1\n" +
            "A=M\n" +
            "D=M\n" +
            "A=A-1\n" +
            "D=M-D\n" +
            "@EQ_{INDEX}\n" +
            "D;JEQ\n" +
            "@SP\n" +
            "A=M-1\n" +
            "M=0\n" +
            "@END_{INDEX}\n" +
            "0;JMP\n" +
            "(EQ_{INDEX})\n" +
            "@SP\n" +
            "A=M-1\n" +
            "M=-1\n" +
            "(END_{INDEX})\n"),
    GT(
            "// GT\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "A=A-1\n" +
                    "D=M-D\n" +
                    "@GT_{INDEX}\n" +
                    "D;JGT\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "M=0\n" +
                    "@END_{INDEX}\n" +
                    "0;JMP\n" +
                    "(GT_{INDEX})\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "M=-1\n" +
                    "(END_{INDEX})\n"
    ),
    LT(
            "// lt\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "A=A-1\n" +
                    "D=M-D\n" +
                    "@LT_{INDEX}\n" +
                    "D;JLT\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "M=0\n" +
                    "@END_{INDEX}\n" +
                    "0;JMP\n" +
                    "(LT_{INDEX})\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "M=-1\n" +
                    "(END_{INDEX})\n"
    ),
    AND(
            "//AND\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "A=A-1\n" +
                    "M=D&M\n"
    ),
    OR(
            "// or\n" +
                    "@SP\n" +
                    "M=M-1\n" +
                    "A=M\n" +
                    "D=M\n" +
                    "A=A-1\n" +
                    "M=D|M\n"
    ),
    NOT(
            "// not\n" +
                    "@SP\n" +
                    "A=M-1\n" +
                    "M=!M"
    );

    private final String assemblyTemplate;

    VmCommand(String assemblyTemplate) {
        this.assemblyTemplate = assemblyTemplate;
    }

    public String getAssemblyCode(String segment, int index) {
        return assemblyTemplate.replace("{INDEX}", String.valueOf(index))
                .replace("{SEGMENT}", segment);
    }
    public String getAssemblyCode(String segment, int id,int index) {
        return assemblyTemplate.replace("{INDEX}", String.valueOf(index))
                .replace("{SEGMENT}", segment)
                .replace("{ID}", String.valueOf(id));
    }
}

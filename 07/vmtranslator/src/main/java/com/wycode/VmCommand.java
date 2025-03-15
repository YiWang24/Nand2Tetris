package com.wycode;

public enum VmCommand {
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
}

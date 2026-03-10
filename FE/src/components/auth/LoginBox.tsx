"use client";

import { Field } from "@/components/ui/field";
import { PasswordInput } from "@/components/ui/password-input";
import { toaster } from "@/components/ui/toaster";
import { CODE_REGEX } from "@/constants/code-regex";
import { PASSWORD_REGEX } from "@/constants/password-regex";
import { useLoginMutation } from "@/service/api/authApiSlice";
import { setCredentials } from "@/service/features/authSlice";
import { useAppDispatch } from "@/service/hooks";
import { devlog } from "@/utils/devlog";
import { tryGetApiErrorMsg } from "@/utils/tryGetApiErrorMsg";
import {
  Alert,
  Button,
  CardBody,
  CardDescription,
  CardFooter,
  CardHeader,
  CardRoot,
  CardTitle,
  Input,
  Link as ChakraLink,
  Stack,
} from "@chakra-ui/react";
import check from "check-types";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { ColorModeButton } from "../ui/color-mode";
import { config } from "@/config/config";

type LoginFormFields = {
  code: string
  password: string
}

export default function LoginBox() {
  const {
    register,
    handleSubmit,
    watch,
    control,
    formState: { errors },
    trigger,
  } = useForm<LoginFormFields>()

  const router = useRouter();

  devlog("hi");

  const dispatch = useAppDispatch();

  const [login, { isLoading: isLoggingIn, error: logInError }] =
    useLoginMutation();

  const onSubmit: SubmitHandler<LoginFormFields> = async (data) => {
    console.log(data)

    try {
      const response = await login(data).unwrap();
      const userData = response.data?.userState;
      if (!check.nonEmptyObject(userData)) {
        throw "Invalid login response";
      }
      dispatch(setCredentials(userData));
      toaster.create({
        description: `Đăng nhập thành công bằng tài khoản người dùng ${userData?.name}`,
        type: "success",
      });
      router.push("/");
    } catch { }
  }

  return (
    <CardRoot w="lg">
      <CardHeader>
        <CardTitle>Đăng nhập</CardTitle>
        <CardDescription>
          <ColorModeButton />
        </CardDescription>
      </CardHeader>
      <CardBody>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Stack gap="4" w="full">
            <Field label="Mã đăng nhập">
              <Controller
                name="code"
                control={control}
                rules={{ required: true, pattern: CODE_REGEX, min: config.MIN_USER_CODE_LENGTH, max: config.MAX_USER_CODE_LENGTH, onChange: () => { trigger("code") } }}
                render={({ field }) => <Input {...field} />}
              />
            </Field>
            <Field label="Mật khẩu">
              <Controller
                name="password"
                control={control}
                rules={{ required: true, pattern: PASSWORD_REGEX, min: config.MIN_PASSWORD_LENGTH, max: config.MAX_PASSWORD_LENGTH, onChange: () => { trigger("password") } }}
                render={({ field }) => <PasswordInput {...field} />}
              />
            </Field>
            {logInError && (
              <Alert.Root status={"error"}>
                <Alert.Indicator />
                <Alert.Content>
                  <Alert.Title>Failed to login</Alert.Title>
                  <Alert.Description>
                    {tryGetApiErrorMsg(logInError)}
                  </Alert.Description>
                </Alert.Content>
              </Alert.Root>
            )}
          </Stack>
        </form>
      </CardBody>
      <CardFooter justifyContent="flex-end">
        <Link href={"/"}>
          <Button variant="outline">Về trang chủ</Button>
        </Link>
        <Button disabled={!!errors.code || !!errors.password} colorPalette={"blue"} variant="solid" onClick={handleSubmit(onSubmit)}>Đăng nhập</Button>
      </CardFooter>
    </CardRoot>
  );
}

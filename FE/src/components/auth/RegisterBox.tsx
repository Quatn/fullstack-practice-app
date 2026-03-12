"use client";

import { Field } from "@/components/ui/field";
import { PasswordInput } from "@/components/ui/password-input";
import { toaster } from "@/components/ui/toaster";
import { CODE_REGEX } from "@/constants/code-regex";
import { PASSWORD_REGEX } from "@/constants/password-regex";
import { useLoginMutation, useRegisterMutation } from "@/service/api/authApiSlice";
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
import { EMAIL_REGEX } from "@/constants/email-regex";

type RegisterFormFields = {
  code: string;
  email: string;
  name: string;
  password: string;
  repeatPassword: string;
}

export default function RegisterBox() {
  const {
    handleSubmit,
    control,
    formState: { errors },
    trigger,
  } = useForm<RegisterFormFields>()

  const router = useRouter();

  const dispatch = useAppDispatch();

  const [register, { isLoading: isLoggingIn, error: logInError }] =
    useRegisterMutation();

  const onSubmit: SubmitHandler<RegisterFormFields> = async (data) => {
    try {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const { repeatPassword: _, ...body } = data;
      const response = await register(body).unwrap();
      const userData = response.data?.userState;
      if (!check.nonEmptyObject(userData)) {
        throw "Invalid register response";
      }
      /*
      dispatch(setCredentials(userData));
      */
      toaster.create({
        description: `Đăng kí thành công tài khoản người dùng ${userData?.name}`,
        type: "success",
      });
      router.push("/");
    } catch { }
  }

  return (
    <CardRoot w="lg">
      <CardHeader>
        <CardTitle>Đăng kí</CardTitle>
        <CardDescription>
          Or <Link href={"login"}><ChakraLink color={"blue.500"} as="span">login</ChakraLink></Link> using an existing account
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

            <Field label="Email">
              <Controller
                name="email"
                control={control}
                rules={{ required: true, pattern: EMAIL_REGEX, onChange: () => { trigger("email") } }}
                render={({ field }) => <Input {...field} />}
              />
            </Field>

            <Field label="Tên người dùng">
              <Controller
                name="name"
                control={control}
                rules={{ required: true, min: config.MIN_USER_NAME_LENGTH, max: config.MAX_USER_NAME_LENGTH, onChange: () => { trigger("name") } }}
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

            <Field label="Nhập lại mật khẩu">
              <Controller
                name="repeatPassword"
                control={control}
                rules={{ required: true, pattern: PASSWORD_REGEX, min: config.MIN_PASSWORD_LENGTH, max: config.MAX_PASSWORD_LENGTH, validate: () => true, onChange: () => { trigger("repeatPassword") } }}
                render={({ field }) => <PasswordInput {...field} />}
              />
            </Field>

            {logInError && (
              <Alert.Root status={"error"}>
                <Alert.Indicator />
                <Alert.Content>
                  <Alert.Title>Failed to register</Alert.Title>
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

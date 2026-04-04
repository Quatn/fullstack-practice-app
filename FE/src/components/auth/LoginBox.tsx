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
  SkeletonText,
  Badge,
  Skeleton,
} from "@chakra-ui/react";
import check from "check-types";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { ColorModeButton } from "../ui/color-mode";
import { config } from "@/config/config";
import { useFormatMessage } from "@/lib/intl/useFormatMessage";
import { tryGetApiErrorCode } from "@/utils/tryGetApiErrorCode";
import { LocaleKey } from "@/lib/intl/intl";
import { FormattedText } from "@/lib/intl/FormattedText";

const FText = ({ id }: { id: LocaleKey }) => {
  return (
    <FormattedText
      as={"span"}
      id={id}
      placeholder={<Skeleton as={"span"} mx={1} height={"1rem"} width={"5rem"} display={"inline-block"} />}
    />
  )
}

type LoginFormFields = {
  loginKey: string
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
  const t = useFormatMessage();

  const router = useRouter();

  const dispatch = useAppDispatch();

  const [login, { isLoading: isLoggingIn, error: logInError }] =
    useLoginMutation();

  const onSubmit: SubmitHandler<LoginFormFields> = async (data) => {
    try {
      const response = await login(data).unwrap();
      const userData = response.data?.userState;
      if (!check.nonEmptyObject(userData)) {
        throw "Invalid login response";
      }
      toaster.create({
        title: (check.string(userData?.name)) ?
          t("auth.login.result.success.info", { userInfo: userData?.name })
          : t("auth.login.result.success"),
        type: "success",
      });
      router.push("/");
    } catch (e) {
      const error = tryGetApiErrorCode(e);
      toaster.create({
        title: t("auth.login.result.failed"),
        description: t(`errors.auth.login.${error}` as unknown as LocaleKey,
          {},
          t("auth.login.result.failed.defaultError")),
        type: "error",
      });
    }
  }

  return (
    <CardRoot w="lg">
      <CardHeader>
        <CardTitle>
          <FText id="auth.login.title" />
        </CardTitle>
        <CardDescription>
          <FText id="words.Or" /> <Link href={"register"}><ChakraLink color={"blue.500"} as="span">{
            <FText id="auth.login.form.link.register" />
          }</ChakraLink> </Link> <FText id="auth.login.form.description" />
        </CardDescription>
      </CardHeader>
      <CardBody>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Stack gap="4" w="full">
            <Field label={<FText id="auth.login.form.label.loginKey" />}>
              <Controller
                name="loginKey"
                control={control}
                rules={{ required: true, pattern: CODE_REGEX, min: config.MIN_USER_CODE_LENGTH, max: config.MAX_USER_CODE_LENGTH, onChange: () => { trigger("loginKey") } }}
                render={({ field }) => <Input {...field} />}
              />
            </Field>
            <Field label={<FText id="auth.login.form.label.password" />}>
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
                  <Alert.Title>{t("auth.login.result.failed")}</Alert.Title>
                  <Alert.Description>
                    {t(`errors.auth.login.${tryGetApiErrorCode(logInError)}` as unknown as LocaleKey,
                      {},
                      t("auth.login.result.failed.defaultError"))}
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
        <Button disabled={!!errors.loginKey || !!errors.password} colorPalette={"blue"} variant="solid" onClick={handleSubmit(onSubmit)}>Đăng nhập</Button>
      </CardFooter>
    </CardRoot>
  );
}

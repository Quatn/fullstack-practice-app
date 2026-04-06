"use client";

import { Field } from "@/components/ui/field";
import { PasswordInput } from "@/components/ui/password-input";
import { toaster } from "@/components/ui/toaster";
import { useLoginMutation } from "@/service/api/authApiSlice";
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
  Spinner,
} from "@chakra-ui/react";
import check from "check-types";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { config } from "@/config/config";
import { useFormatMessage } from "@/lib/intl/useFormatMessage";
import { tryGetApiErrorCode } from "@/utils/tryGetApiErrorCode";
import { LocaleKey } from "@/lib/intl/intl";
import { FormattedTextWithSkeletonPlaceholder as T } from "@/components/presets/FormattedTextWithSkeletonPlaceholder";

type LoginFormFields = {
  loginKey: string
  password: string
}

export default function LoginBox() {
  const t = useFormatMessage();
  const {
    handleSubmit,
    control,
    formState: { errors },
    trigger,
  } = useForm<LoginFormFields>()

  const router = useRouter();

  const [login, { isLoading: isLoggingIn, error: logInError }] =
    useLoginMutation();

  const onSubmit: SubmitHandler<LoginFormFields> = async (data) => {
    try {
      const response = await login(data).unwrap();
      const userData = response.data?.userState;
      if (!check.nonEmptyObject(userData)) {
        // TODO
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

  const formErrorParse = (err: keyof LoginFormFields): string | undefined => {
    switch (err) {
      case "loginKey":
        switch (errors[err]?.type) {
          case "minLength":
          case "maxLength":
            return t("auth.login.form.error.loginKey.length",
              {
                min: config.MIN_USER_CODE_LENGTH,
                max: config.MAX_USER_CODE_LENGTH
              });
          case "required":
            return t("auth.login.form.error.loginKey.required");
          default:
            return undefined;
        }

      case "password":
        switch (errors[err]?.type) {
          case "minLength":
          case "maxLength":
            return t("auth.login.form.error.password.length", {
              min: config.MIN_PASSWORD_LENGTH,
              max: config.MAX_PASSWORD_LENGTH
            });
          case "required":
            return t("auth.login.form.error.password.required");
          default:
            return undefined;
        }

      default:
        return undefined;
    }
  }

  return (
    <CardRoot w="lg">
      <CardHeader>
        <CardTitle>
          <T id="auth.login.title" />
        </CardTitle>
        <CardDescription>
          <T id="words.Or" /> <Link href={"register"}><ChakraLink color={"blue.500"} as="span">{
            <T id="auth.login.link.register" />
          }</ChakraLink> </Link> <T id="auth.login.form.description" />
        </CardDescription>
      </CardHeader>
      <CardBody>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Stack gap="4" w="full">
            <Field
              label={<T id="auth.login.form.label.loginKey" />}
              errorText={formErrorParse("loginKey")}
              invalid={!!errors.loginKey}
            >
              <Controller
                name="loginKey"
                control={control}
                rules={{ required: true, minLength: config.MIN_USER_CODE_LENGTH, maxLength: config.MAX_USER_CODE_LENGTH, onChange: () => { trigger("loginKey") } }}
                render={({ field }) => <Input {...field} />}
              />
            </Field>

            <Field
              label={<T id="auth.login.form.label.password" />}
              errorText={formErrorParse("password")}
              invalid={!!errors.password}
            >
              <Controller
                name="password"
                control={control}
                rules={{ required: true, minLength: config.MIN_PASSWORD_LENGTH, maxLength: config.MAX_PASSWORD_LENGTH, onChange: () => { trigger("password") } }}
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
          <Button variant="outline"><T id="auth.login.link.home" /></Button>
        </Link>
        <Button
          disabled={!!errors.loginKey || !!errors.password || isLoggingIn}
          colorPalette={"blue"}
          variant="solid"
          onClick={handleSubmit(onSubmit)}
        >
          <T id='auth.login.form.button.submit' /> {isLoggingIn && <Spinner />}
        </Button>
      </CardFooter>
    </CardRoot>
  );
}

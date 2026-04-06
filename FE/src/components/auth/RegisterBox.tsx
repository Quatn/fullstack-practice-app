"use client";

import { Field } from "@/components/ui/field";
import { PasswordInput } from "@/components/ui/password-input";
import { toaster } from "@/components/ui/toaster";
import { CODE_REGEX } from "@/constants/code-regex";
import { PASSWORD_REGEX } from "@/constants/password-regex";
import { useRegisterMutation } from "@/service/api/authApiSlice";
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
import { EMAIL_REGEX } from "@/constants/email-regex";
import { useFormatMessage } from "@/lib/intl/useFormatMessage";
import { tryGetApiErrorCode } from "@/utils/tryGetApiErrorCode";
import { LocaleKey } from "@/lib/intl/intl";
import { FormattedTextWithSkeletonPlaceholder as T } from "@/components/presets/FormattedTextWithSkeletonPlaceholder";

type RegisterFormFields = {
  code: string;
  email: string;
  name: string;
  password: string;
  repeatPassword: string;
}

export default function RegisterBox() {
  const t = useFormatMessage();
  const {
    handleSubmit,
    control,
    formState: { errors },
    trigger,
    getValues,
  } = useForm<RegisterFormFields>()

  const router = useRouter();

  const [register, { isLoading: isRegistering, error: registerError }] =
    useRegisterMutation();

  const onSubmit: SubmitHandler<RegisterFormFields> = async (data) => {
    try {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const { repeatPassword: _, ...body } = data;
      const response = await register(body).unwrap();
      const userData = response.data?.userState;
      if (!check.nonEmptyObject(userData)) {
        // TODO
        throw "Invalid register response";
      }
      toaster.create({
        title: (check.string(userData?.name)) ?
          t("auth.register.result.success.info", { userInfo: userData?.name })
          : t("auth.register.result.success"),
        type: "success",
      });
      router.push("/");
    } catch (e) {
      const error = tryGetApiErrorCode(e);
      toaster.create({
        title: t("auth.register.result.failed"),
        description: t(`errors.auth.register.${error}` as unknown as LocaleKey,
          {},
          t("auth.register.result.failed.defaultError")),
        type: "error",
      });
    }
  }

  const formErrorParse = (err: keyof RegisterFormFields): string | undefined => {
    switch (err) {
      case "code":
        switch (errors[err]?.type) {
          case "minLength":
          case "maxLength":
            return t("auth.register.form.error.code.length", {
              min: config.MIN_USER_CODE_LENGTH,
              max: config.MAX_USER_CODE_LENGTH
            });
          case "pattern":
            return t("auth.register.form.error.code.pattern");
          case "required":
            return t("auth.register.form.error.code.required");
          default:
            return undefined;
        }

      case "email":
        switch (errors[err]?.type) {
          case "pattern":
            return t("auth.register.form.error.email.pattern");
          case "required":
            return t("auth.register.form.error.email.required");
          default:
            return undefined;
        }

      case "name":
        switch (errors[err]?.type) {
          /*
          case "minLength":
          case "maxLength":
            return t("auth.register.form.error.name.length", {
              min: config.MIN_NAME_LENGTH,
              max: config.MAX_NAME_LENGTH
            });
          */
          case "required":
            return t("auth.register.form.error.name.required");
          default:
            return undefined;
        }

      case "password":
        switch (errors[err]?.type) {
          case "minLength":
          case "maxLength":
            return t("auth.register.form.error.password.length", {
              min: config.MIN_PASSWORD_LENGTH,
              max: config.MAX_PASSWORD_LENGTH
            });
          case "pattern":
            return t("auth.register.form.error.password.pattern");
          case "required":
            return t("auth.register.form.error.password.required");
          default:
            return undefined;
        }

      case "repeatPassword":
        switch (errors[err]?.type) {
          case "validate":
            return t("auth.register.form.error.repeatPassword.validate");
          case "required":
            return t("auth.register.form.error.repeatPassword.required");
          default:
            return undefined;
        }

      default:
        return undefined;
    }
  };

  return (
    <CardRoot w="lg">
      <CardHeader>
        <CardTitle>
          <T id="auth.register.title" />
        </CardTitle>
        <CardDescription>
          <T id="words.Or" /> <Link href={"login"}><ChakraLink color={"blue.500"} as="span">{
            <T id="auth.register.link.login" />
          }</ChakraLink> </Link> <T id="auth.register.form.description" />
        </CardDescription>
      </CardHeader>
      <CardBody>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Stack gap="4" w="full">
            <Field
              label={<T id="auth.register.form.label.code" />}
              errorText={formErrorParse("code")}
              invalid={!!errors.code}
            >
              <Controller
                name="code"
                control={control}
                rules={{
                  required: true,
                  minLength: config.MIN_USER_CODE_LENGTH,
                  maxLength: config.MAX_USER_CODE_LENGTH,
                  pattern: CODE_REGEX,
                  onChange: () => { trigger("code") }
                }}
                render={({ field }) => <Input {...field} />}
              />
            </Field>

            <Field
              label={<T id="auth.register.form.label.email" />}
              errorText={formErrorParse("email")}
              invalid={!!errors.email}
            >
              <Controller
                name="email"
                control={control}
                rules={{
                  required: true,
                  minLength: config.MIN_USER_CODE_LENGTH,
                  maxLength: config.MAX_USER_CODE_LENGTH,
                  pattern: EMAIL_REGEX,
                  onChange: () => { trigger("email") }
                }}
                render={({ field }) => <Input {...field} />}
              />
            </Field>

            <Field
              label={<T id="auth.register.form.label.name" />}
              errorText={formErrorParse("name")}
              invalid={!!errors.name}
            >
              <Controller
                name="name"
                control={control}
                rules={{
                  required: true,
                  onChange: () => { trigger("name") }
                }}
                render={({ field }) => <Input {...field} />}
              />
            </Field>

            <Field
              label={<T id="auth.register.form.label.password" />}
              errorText={formErrorParse("password")}
              invalid={!!errors.password}
            >
              <Controller
                name="password"
                control={control}
                rules={{
                  required: true,
                  minLength: config.MIN_USER_CODE_LENGTH,
                  maxLength: config.MAX_USER_CODE_LENGTH,
                  pattern: PASSWORD_REGEX,
                  onChange: () => { trigger("password"); trigger("repeatPassword") }
                }}
                render={({ field }) => <PasswordInput {...field} />}
              />
            </Field>

            <Field
              label={<T id="auth.register.form.label.repeatPassword" />}
              errorText={formErrorParse("repeatPassword")}
              invalid={!!errors.repeatPassword}
            >
              <Controller
                name="repeatPassword"
                control={control}
                rules={{
                  required: true,
                  validate: () => { return getValues("password") === getValues("repeatPassword") },
                  onChange: () => { trigger("repeatPassword") }
                }}
                render={({ field }) => <PasswordInput {...field} />}
              />
            </Field>

            {registerError && (
              <Alert.Root status={"error"}>
                <Alert.Indicator />
                <Alert.Content>
                  <Alert.Title>{t("auth.register.result.failed")}</Alert.Title>
                  <Alert.Description>
                    {t(`errors.auth.register.${tryGetApiErrorCode(registerError)}` as unknown as LocaleKey,
                      {},
                      t("auth.register.result.failed.defaultError"))}
                  </Alert.Description>
                </Alert.Content>
              </Alert.Root>
            )}

          </Stack>
        </form>
      </CardBody>
      <CardFooter justifyContent="flex-end">
        <Link href={"/"}>
          <Button variant="outline"><T id="auth.register.link.home" /></Button>
        </Link>
        <Button
          disabled={!!errors.code
            || !!errors.name
            || !!errors.email
            || !!errors.password
            || !!errors.repeatPassword
            || isRegistering
          }
          colorPalette={"blue"}
          variant="solid"
          onClick={handleSubmit(onSubmit)}
        >
          <T id='auth.register.form.button.submit' /> {isRegistering && <Spinner />}
        </Button>
      </CardFooter>
    </CardRoot>
  );
}

import { Field } from "@/components/ui/field";
import { toaster } from "@/components/ui/toaster";
import { ConversationType } from "@/constants/enum/conversation-type";
import { useFormatMessage } from "@/lib/intl/useFormatMessage";
import { useCreateConversationMutation } from "@/service/api/conversationApiSlice";
import { tryGetApiErrorCode } from "@/utils/tryGetApiErrorCode";
import { Alert, createListCollection, Input, Portal, Select, Stack } from "@chakra-ui/react";
import check from "check-types";
import { useRouter } from "next/router";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { FormattedTextWithSkeletonPlaceholder as T } from "@/components/presets/FormattedTextWithSkeletonPlaceholder";
import { config } from "@/config/config";
import { LocaleKey } from "@/lib/intl/intl";

type CreateConversationFormFields = {
  name: string;
  type: ConversationType;
  coverUrl: string;
}

const conversationTypeCol = createListCollection({
  items: [
    { label: "Direct Messages", value: "dm" },
    { label: "Rooms", value: "room" },
  ],
})

export default function ConversationCreateForm() {
  const t = useFormatMessage();
  const {
    handleSubmit,
    control,
    formState: { errors },
    trigger,
  } = useForm<CreateConversationFormFields>()

  const router = useRouter();

  const [submit, { isLoading: isLoggingIn, error: logInError }] =
    useCreateConversationMutation();

  const onSubmit: SubmitHandler<CreateConversationFormFields> = async (data) => {
    try {
      const response = await submit(data).unwrap();
      const userData = response.data?.conversation;
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
      // TODO
      /*
      toaster.create({
        title: t("auth.login.result.failed"),
        description: t(`errors.auth.login.${error}` as unknown as LocaleKey,
          {},
          t("auth.login.result.failed.defaultError")),
        type: "error",
      });
      */
    }
  }

  const formErrorParse = (err: keyof CreateConversationFormFields): string | undefined => {
    return undefined;
    // TODO
    /*
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
    */
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Stack gap="4" w="full">
        <Field
          label={<T id="auth.login.form.label.loginKey" />}
          errorText={formErrorParse("name")}
          invalid={!!errors.name}
        >
          <Controller
            name="name"
            control={control}
            rules={{ required: true, minLength: config.MIN_USER_CODE_LENGTH, maxLength: config.MAX_USER_CODE_LENGTH, onChange: () => { trigger("name") } }}
            render={({ field }) => <Input {...field} />}
          />
        </Field>

        <Field
          label={<T id="auth.login.form.label.password" />}
          errorText={formErrorParse("type")}
          invalid={!!errors.type}
        >
          <Controller
            name="type"
            control={control}
            rules={{ required: true, minLength: config.MIN_PASSWORD_LENGTH, maxLength: config.MAX_PASSWORD_LENGTH, onChange: () => { trigger("type") } }}
            render={({ field }) =>
              <Select.Root collection={conversationTypeCol} size="sm" width="320px">
                <Select.HiddenSelect />
                <Select.Label>Select conversation</Select.Label>
                <Select.Control>
                  <Select.Trigger>
                    <Select.ValueText placeholder="Select conversation" />
                  </Select.Trigger>
                  <Select.IndicatorGroup>
                    <Select.Indicator />
                  </Select.IndicatorGroup>
                </Select.Control>
                <Portal>
                  <Select.Positioner>
                    <Select.Content>
                      {conversationTypeCol.items.map((convType) => (
                        <Select.Item item={convType} key={convType.value}>
                          {convType.label}
                          <Select.ItemIndicator />
                        </Select.Item>
                      ))}
                    </Select.Content>
                  </Select.Positioner>
                </Portal>
              </Select.Root>
            }
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
  );
}

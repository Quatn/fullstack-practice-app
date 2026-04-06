import { FormattedText } from "@/lib/intl/FormattedText"
import { LocaleKey } from "@/lib/intl/intl"
import { Skeleton } from "@chakra-ui/react"

export const FormattedTextWithSkeletonPlaceholder = ({ id }: { id: LocaleKey }) => {
  return (
    <FormattedText
      as={"span"}
      id={id}
      placeholder={<Skeleton as={"span"} mx={1} height={"1rem"} width={"5rem"} display={"inline-block"} />}
    />
  )
}
